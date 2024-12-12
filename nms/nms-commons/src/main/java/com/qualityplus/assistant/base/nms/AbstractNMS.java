package com.qualityplus.assistant.base.nms;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.qualityplus.assistant.api.nms.NMS;
import com.qualityplus.assistant.api.util.CropUtil;
import com.qualityplus.assistant.base.event.ActionBarMessageEvent;
import com.qualityplus.assistant.base.event.ActionBarMessageEvent.ActionBarType;
import eu.okaeri.commons.bukkit.UnsafeBukkitCommons;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Abstract NMS Implementation
 */
public abstract class AbstractNMS implements NMS {
    protected final Cache<Block, Integer> clickCache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();
    private final Map<UUID, Long> disabled = new HashMap<>();
    private final Map<UUID, Long> enabled = new HashMap<>();
    protected static BossBar bossBar;
    private static Method metaSetProfileMethod;
    private static Field metaProfileField;

    /**
     *
     * @param itemStack {@link ItemStack}
     * @param texture   {@link String}
     * @return {@link ItemStack}
     */
    @Override
    public ItemStack setTexture(final ItemStack itemStack, final String texture) {
        try {
            final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

            mutateItemMeta(meta, texture);
            itemStack.setItemMeta(meta);

            return itemStack;
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    private void mutateItemMeta(final SkullMeta meta, final String b64) {
        try {
            if (metaSetProfileMethod == null) {
                metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                metaSetProfileMethod.setAccessible(true);
            }
            metaSetProfileMethod.invoke(meta, makeProfile(b64));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            // if in an older API where there is no setProfile method,
            // we set the profile field directly.
            try {
                if (metaProfileField == null) {
                    metaProfileField = meta.getClass().getDeclaredField("profile");
                    metaProfileField.setAccessible(true);
                }
                metaProfileField.set(meta, makeProfile(b64));

            } catch (NoSuchFieldException | IllegalAccessException ex2) {
                ex2.printStackTrace();
            }
        }
    }


    private static GameProfile makeProfile(final String b64) {
        // random uuid based on the b64 string
        final UUID id = new UUID(
                b64.substring(b64.length() - 20).hashCode(),
                b64.substring(b64.length() - 10).hashCode()
        );
        final GameProfile profile = new GameProfile(id, "Player");
        profile.getProperties().put("textures", new Property("textures", b64));
        return profile;
    }

    @Override
    public void sendActionBar(final Player player, final String message) {
        if (!player.isOnline()) {
            return;
        }

        final ActionBarType type = ActionBarType.ACTION_BAR_TEXT;

        try {
            UnsafeBukkitCommons.sendMessage(player, message, UnsafeBukkitCommons.ChatTarget.ACTION_BAR);
            final Event event = new ActionBarMessageEvent(player, message, type);

            Bukkit.getPluginManager().callEvent(event);
        } catch (final IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTitle(final Player player, final String title, final String subtitle,
                          final int fadeIn, final int stay, final int fadeOut) {
        UnsafeBukkitCommons.sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
    }


    @Override
    public int getMaxAge(final Block block) {
        return CropUtil.getMaxAge(block);
    }


    /**
     * Black list an uuid in action bar
     *
     * @param uuid {@link UUID}
     */
    public void blacklist(final UUID uuid) {
        if (isWhitelisted(uuid)) {
            return;
        }

        this.disabled.put(uuid, System.currentTimeMillis() + 3000);
    }

    /**
     * Retrieves if a UUID is blacklisted
     *
     * @param uuid {@link UUID}
     * @return true if it's blacklisted
     */
    public boolean isBlacklisted(final UUID uuid) {
        if (!this.disabled.containsKey(uuid)) {
            return false;
        }
        final long endTime = this.disabled.get(uuid);

        return endTime > System.currentTimeMillis();
    }

    /**
     * White list temporally an uuid
     *
     * @param uuid {@link UUID}
     */
    public void whitelistTemp(final UUID uuid) {
        this.enabled.put(uuid, System.currentTimeMillis() + 50);
    }

    /**
     * Retrieves if a UUID is whitelisted
     *
     * @param uuid {@link UUID}
     * @return true if it's whitelisted
     */
    public boolean isWhitelisted(final UUID uuid) {
        if (!this.enabled.containsKey(uuid)) {
            return false;
        }
        final long endTime = this.enabled.get(uuid);
        return endTime > System.currentTimeMillis();
    }

}
