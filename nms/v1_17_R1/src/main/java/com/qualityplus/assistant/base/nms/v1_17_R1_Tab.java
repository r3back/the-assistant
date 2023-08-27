package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.qualityplus.assistant.api.nms.tab.TabAdapter;
import com.qualityplus.assistant.api.nms.tab.skin.SkinType;
import com.qualityplus.assistant.api.team.TeamInfo;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * NMS Tab Implementation for Spigot v1_17_R1
 */
public final class v1_17_R1_Tab extends TabAdapter {
    private final Map<Player, GameProfile[]> profiles = new HashMap<>();
    private final List<Player> initialized = new ArrayList<>();
    private static final Integer MAX_SLOTS = 24;

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
            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(
                    Objects.requireNonNull(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}")),
                    Objects.requireNonNull(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}"))
            );

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

            this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entityPlayer);
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
     * Send an entry's data to a player
     *
     * @param player the player
     * @param axis   the axis of the entry
     * @param ping   the ping to display on the entry's position
     * @param text   the text to display on the entry's position
     * @return the current adapter instance
     */
    @Override
    public TabAdapter sendEntryData(final Player player, final int axis, final int ping, final String text) {
        final GameProfile profile = this.profiles.get(player)[axis];
        final EntityPlayer entityPlayer = this.getEntityPlayer(profile);

        entityPlayer.listName = new ChatComponentText(text);
        entityPlayer.e = ping;
        // the fuck happened to spigot 1.17

        setFakePing(entityPlayer.getBukkitEntity());

        this.setupScoreboard(player, text, profile.getName());
        this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.c, entityPlayer);

        return this;
    }

    /**
     * Add fake players to the player's tablist
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

                //setFake(entityPlayer.getBukkitEntity());

                this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entityPlayer);
            }

            this.initialized.add(player);
        }

        return this;
    }

    /**
     * Set Fake ping to player
     *
     * @param play {@link Player}
     */
    public void setFakePing(final Player play) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard playerBoard = p.getScoreboard();

            if (playerBoard == null) {
                playerBoard = Bukkit.getScoreboardManager().getNewScoreboard();
            }
            Objective ob = playerBoard.getObjective("PingTab");

            if (ob == null) {
                playerBoard.registerNewObjective("PingTab", "dummy").setDisplaySlot(DisplaySlot.PLAYER_LIST);
                ob = playerBoard.getObjective("PingTab");
            }

            final int ping = ((CraftPlayer) play).getHandle().e;
            ob.getScore(play).setScore(ping);


            final TeamInfo teamInfo = TabAdapter.PLAYER_COLORS.getOrDefault(p.getName(), null);

            if (teamInfo != null) {

                Team existentTeam = playerBoard.getTeam(teamInfo.getName());

                if (existentTeam == null) {

                    final Team team = playerBoard.registerNewTeam(teamInfo.getName());

                    team.setColor(teamInfo.getChatColor());

                    existentTeam = team;
                }

                if (!existentTeam.hasEntry(p.getName())) {
                    existentTeam.addEntry(p.getName());
                }
            }

            p.setScoreboard(playerBoard);
        }
    }

    /**
     * Get an entity player by a profile
     *
     * @param profile the profile
     * @return the entity player
     */
    private EntityPlayer getEntityPlayer(final GameProfile profile) {
        final MinecraftServer server = MinecraftServer.getServer();

        final World world = Bukkit.getServer().getWorlds().get(1);

        final WorldServer worldServer = server.getWorlds().iterator().next();

        return new EntityPlayer(server, worldServer, profile);
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
            this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, target);
        }

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
            final ChannelPipeline pipeline = this.getPlayerConnection(player).a.k.pipeline();

            while (pipeline.get("packet_handler") == null) {
                this.showRealPlayers(player);
            }


            pipeline.addBefore("packet_handler", player.getName(), this.createShowListener(player));
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
        this.sendInfoPacket(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, target);

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
        return ((CraftPlayer) player).getHandle().b;
    }

    /**
     * Send the {@link PacketPlayOutPlayerInfo} to a player
     *
     * @param player the player
     * @param action the action
     * @param target the target
     */
    private void sendInfoPacket(final Player player, final  PacketPlayOutPlayerInfo.EnumPlayerInfoAction action, final EntityPlayer target) {
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
