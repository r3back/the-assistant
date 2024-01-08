package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.qualityplus.assistant.api.nms.tab.TabAdapter;
import com.qualityplus.assistant.api.nms.tab.skin.SkinType;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * NMS Tab Implementation for Spigot v1_12_R1
 */
public final class v1_12_R1_Tab extends TabAdapter {
    private static final Integer MAX_SLOTS = 24;
    private final Map<Player, GameProfile[]> profiles = new HashMap<>();
    private final List<Player> initialized = new ArrayList<>();

    /**
     * Send a packet to the player
     *
     * @param player the player
     * @param packet the packet to send
     */
    private void sendPacket(final Player player, final Packet<?> packet) {
        this.getPlayerConnection(player).sendPacket(packet);
    }

    /**
     * Send the header and footer to a player
     *
     * @param player the player to send the header and footer to
     * @param header the header to send
     * @param footer the footer to send
     * @return the current adapter instance
     */
    @Override
    public TabAdapter sendHeaderFooter(final Player player, final String header, final String footer) {
        if (header != null || footer != null) {
            final Packet<?> packet = new PacketPlayOutPlayerListHeaderFooter();

            try {
                final Field headerField = packet.getClass().getDeclaredField("a");
                final Field footerField = packet.getClass().getDeclaredField("b");

                headerField.setAccessible(true);
                footerField.setAccessible(true);

                headerField.set(packet, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}"));
                footerField.set(packet, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}"));

            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            this.sendPacket(player, packet);
        }

        return this;
    }


    /**
     * Update the skin on the tablist for a player
     *
     * @param skinDataParam the data of the new skin
     * @param index         the index of the profile
     * @param player        the player to update the skin for
     */
    @Override
    public void updateSkin(final String[] skinDataParam, final int index, final Player player) {
        final GameProfile profile = this.profiles.get(player)[index];
        final Property property = profile.getProperties().get("textures").iterator().next();
        final EntityPlayer entityPlayer = this.getEntityPlayer(profile);
        String[] skinData = skinDataParam;
        skinData = skinData != null && skinData.length >= 1 && !skinData[0].isEmpty() && !skinData[1].isEmpty()
                ? skinData
                : SkinType.DARK_GRAY.getSkinData();

        if (!property.getSignature().equals(skinData[1]) || !property.getValue().equals(skinData[0])) {
            profile.getProperties().remove("textures", property);
            profile.getProperties().put("textures", new Property("textures", skinData[0], skinData[1]));

            this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
        }
    }


    /**
     * Check if the player should be able to see the fourth row
     *
     * @param player the player
     * @return whether they should be able to see the fourth row
     */
    @Override
    public int getMaxElements(final Player player) {
        return MAX_SLOTS;
    }

    /**
     * Send an entry"s data to a player
     *
     * @param player   the player
     * @param axis     the axis of the entry
     * @param ping     the ping to display on the entry"s position
     * @param text     the text to display on the entry"s position
     * @return the current adapter instance
     */
    @Override
    public TabAdapter sendEntryData(final Player player, final int axis, final int ping, final String text) {
        final GameProfile profile = this.profiles.get(player)[axis];
        final EntityPlayer entityPlayer = this.getEntityPlayer(profile);

        entityPlayer.listName = new ChatComponentText(text);
        entityPlayer.ping = ping;

        this.setupScoreboard(player, text, profile.getName());

        this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, entityPlayer);
        this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, entityPlayer);

        return this;
    }

    /**
     * Add fake players to the player"s tablist
     *
     * @param player the player to send the fake players to
     * @return the current adapter instance
     */
    @Override
    public TabAdapter addFakePlayers(final Player player) {
        if (!this.initialized.contains(player)) {
            for (int i = 0; i < MAX_SLOTS; i++) {
                final GameProfile profile = this.profiles.get(player)[i];
                final EntityPlayer entityPlayer = this.getEntityPlayer(profile);

                this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
            }

            this.initialized.add(player);
        }

        return this;
    }

    /**
     * Get an entity player by a profile
     *
     * @param profile the profile
     * @return the entity player
     */
    private EntityPlayer getEntityPlayer(final GameProfile profile) {
        final MinecraftServer server = MinecraftServer.getServer();
        final PlayerInteractManager interactManager = new PlayerInteractManager(server.getWorldServer(0));

        return new EntityPlayer(server, server.getWorldServer(0), profile, interactManager);
    }

    /**
     * Hide all real players from the tab
     *
     * @param player the player
     * @return the current adapter instance
     */
    @Override
    public TabAdapter hideRealPlayers(final Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            this.hidePlayer(player, target);
        }

        return this;
    }

    /**
     * Hide a real player om the tablist
     *
     * @param player the player to hide the player from
     * @param target the player to hide
     * @return the current adapter instance
     */
    @Override
    public TabAdapter hidePlayer(final Player player, final Player target) {
        if (player.canSee(target) || target.equals(player)) {
            this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, target);
        }

        return this;
    }

    /**
     * Show a real player to a player
     *
     * @param player the player
     * @param target the player to show to the other player
     * @return the current adapter instance
     */
    @Override
    public TabAdapter showPlayer(final Player player, final Player target) {
        this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, target);

        return this;
    }

    /**
     * Show all real players on the tab
     *
     * @param player the player
     * @return the current adapter instance
     */
    @Override
    public TabAdapter showRealPlayers(final Player player) {
        if (!this.initialized.contains(player)) {
            this.getPlayerConnection(player).networkManager.channel.pipeline().addFirst(
                    this.createShowListener(player)
            );
        }

        return this;
    }

    /**
     * Create the listener required to show the players
     *
     * @param player the player to create it for
     * @return the handler
     */
    private ChannelDuplexHandler createShowListener(final Player player) {
        return new ChannelDuplexHandler() {
            @Override
            public void write(final ChannelHandlerContext context, final Object packet, final ChannelPromise promise) throws Exception {
                if (packet instanceof PacketPlayOutNamedEntitySpawn) {
                    final PacketPlayOutNamedEntitySpawn entitySpawn = (PacketPlayOutNamedEntitySpawn) packet;
                    final Field uuidField = entitySpawn.getClass().getDeclaredField("b");

                    uuidField.setAccessible(true);

                    final Player target = Bukkit.getPlayer((UUID) uuidField.get(entitySpawn));

                    if (target != null) {
                        showPlayer(player, target);
                    }
                } else if (packet instanceof PacketPlayOutRespawn) {
                    showPlayer(player, player);
                }

                super.write(context, packet, promise);
            }
        };
    }

    /**
     * Get the {@link PlayerConnection} of a player
     *
     * @param player the player to get the player connection object from
     * @return the object
     */
    private PlayerConnection getPlayerConnection(final Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

    /**
     * Send the {@link PacketPlayOutPlayerInfo} to a player
     *
     * @param player the player
     * @param action the action
     * @param target the target
     */
    private void sendInfoPacket(final Player player, final PacketPlayOutPlayerInfo.EnumPlayerInfoAction action, final EntityPlayer target) {
        this.sendPacket(player, new PacketPlayOutPlayerInfo(action, target));
    }

    /**
     * Send the {@link PacketPlayOutPlayerInfo} to a player
     *
     * @param player the player
     * @param action the action
     * @param target the target
     */
    private void sendInfoPacket(final Player player, final PacketPlayOutPlayerInfo.EnumPlayerInfoAction action, final Player target) {
        this.sendInfoPacket(player, action, ((CraftPlayer) target).getHandle());
    }

    /**
     * Create a new game profile
     *
     * @param index  the index of the profile
     * @param text   the text to display
     * @param player the player to make the profiles for
     */
    @Override
    public void createProfiles(final int index, final String text, final Player player) {
        if (!this.profiles.containsKey(player)) {
            this.profiles.put(player, new GameProfile[MAX_SLOTS]);
        }

        if (this.profiles.get(player).length < index + 1 || this.profiles.get(player)[index] == null) {
            final GameProfile profile = new GameProfile(UUID.randomUUID(), text);
            final String[] skinData = SkinType.DARK_GRAY.getSkinData();

            profile.getProperties().put("textures", new Property("textures", skinData[0], skinData[1]));

            this.profiles.get(player)[index] = profile;
        }
    }
}
