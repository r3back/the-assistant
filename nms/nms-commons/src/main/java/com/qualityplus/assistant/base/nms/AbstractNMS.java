package com.qualityplus.assistant.base.nms;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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


    @Override
    public ItemStack setTexture(final ItemStack itemStack, final String texture) {
        try {
            final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

            final UUID uuid = UUID.randomUUID();

            final PlayerProfile playerProfile = Bukkit.createPlayerProfile(uuid, uuid.toString().substring(0, 16));
            final PlayerTextures textures = playerProfile.getTextures();
            final String textureToURL = convertBase64ToURL(texture);
            final URL url = URI.create(textureToURL).toURL();
            textures.setSkin(url);
            playerProfile.setTextures(textures);
            meta.setOwnerProfile(playerProfile);
            itemStack.setItemMeta(meta);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    private String convertBase64ToURL(final String texture) {
        final String jsonString = new String(Base64.getDecoder().decode(texture), StandardCharsets.UTF_8);
        final JSONObject jsonObject = new JSONObject(jsonString);
        final JSONObject jstextures = jsonObject.getJSONObject("textures");
        final JSONObject skin = jstextures.getJSONObject("SKIN");
        return skin.getString("url");
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
