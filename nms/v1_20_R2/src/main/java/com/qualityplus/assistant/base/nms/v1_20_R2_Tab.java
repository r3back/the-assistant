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
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

/**
 * NMS Tab Implementation for Spigot v1_20_R1
 */
public final class v1_20_R2_Tab extends TabAdapter {
    private final Map<Player, GameProfile[]> profiles = new HashMap<>();
    private final List<Player> initialized = new ArrayList<>();
    private static final Integer MAX_SLOTS = 24;
    private static Unsafe unsafe;

    static {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a packet to the player
     *
     * @param player the player
     * @param packet the packet to send
     */
    private void sendPacket(final Player player, final Packet<?> packet) {
        this.getPlayerConnection(player).send(packet);
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
            final ClientboundTabListPacket packet = new ClientboundTabListPacket(
                    Objects.requireNonNull(Component.Serializer.fromJson("{\"text\": \"" + header + "\"}")),
                    Objects.requireNonNull(Component.Serializer.fromJson("{\"text\": \"" + footer + "\"}"))
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
        final ServerPlayer entityPlayer = this.getEntityPlayer(profile);

        String[] skinData = skinDataParam;
        skinData = skinData != null && skinData.length >= 1 && !skinData[0].isEmpty() && !skinData[1].isEmpty()
                ? skinData
                : SkinType.DARK_GRAY.getSkinData();

        if (!property.getSignature().equals(skinData[1]) || !property.getValue().equals(skinData[0])) {
            profile.getProperties().remove("textures", property);
            profile.getProperties().put("textures", new Property("textures", skinData[0], skinData[1]));

            this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, entityPlayer);
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
        final ServerPlayer entityPlayer = this.getEntityPlayer(profile);

        entityPlayer.listName = Component.Serializer.fromJsonLenient(text);

        try {
            final Field pingField = entityPlayer.getClass().getDeclaredField("containerCounter");

            pingField.setAccessible(true);

            pingField.set(entityPlayer, ping);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }

        setFakePing(entityPlayer.getBukkitEntity());

        this.setupScoreboard(player, text, profile.getName());
        this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, entityPlayer);

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
                final ServerPlayer entityPlayer = this.getEntityPlayer(profile);

                //setFake(entityPlayer.getBukkitEntity());

                //this.getPlayerConnection(player).a(new ClientboundPlayerInfoUpdatePacket(Collections.singletonList(getHandle(target).cs())));

                this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, entityPlayer);
            }

            this.initialized.add(player);
        }

        return this;
    }

    /**
     * Set fake ping to player
     *
     * @param play {@link Player}
     */
    public void setFakePing(final Player play) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            try {
                Scoreboard playerBoard = p.getScoreboard();

                if (playerBoard == null) {
                    playerBoard = Bukkit.getScoreboardManager().getNewScoreboard();
                }
                Objective ob = playerBoard.getObjective("PingTab");

                if (ob == null) {
                    playerBoard.registerNewObjective("PingTab", "dummy").setDisplaySlot(DisplaySlot.PLAYER_LIST);
                    ob = playerBoard.getObjective("PingTab");
                }

                final ServerPlayer player = ((CraftPlayer) play).getHandle();

                final Field pingField = player.getClass().getDeclaredField("containerCounter");

                pingField.setAccessible(true);

                final int ping = (int) pingField.get(player);

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
            } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get an entity player by a profile
     *
     * @param profile the profile
     * @return the entity player
     */
    private ServerPlayer getEntityPlayer(final GameProfile profile) {
        final MinecraftServer server = MinecraftServer.getServer();

        final ServerLevel worldServer = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

        return new ServerPlayer(server, worldServer, profile, ClientInformation.createDefault());
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
            this.getPlayerConnection(player).send(new ClientboundPlayerInfoRemovePacket(Collections.singletonList(getHandle(target).getUUID())));

            //this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.a.e, target);
        }

        return this;
    }

    private static net.minecraft.world.entity.Entity getHandle(final org.bukkit.entity.Entity entity) {
        if (!(entity instanceof CraftEntity)) {
            return null;
        }
        return ((CraftEntity) entity).getHandle();
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
            final ServerGamePacketListenerImpl connection = this.getPlayerConnection(player);

            try {

                final Field networkField = connection.getClass().getDeclaredField("h");

                networkField.setAccessible(true);

                final Connection manager = (Connection) unsafe.getObject(connection, unsafe.objectFieldOffset(networkField));

                final ChannelPipeline pipeline = manager.channel.pipeline();

                while (pipeline.get("packet_handler") == null) {
                    this.showRealPlayers(player);
                }

                pipeline.addBefore("packet_handler", player.getName(), this.createShowListener(player));
            } catch (NoSuchFieldException | NoSuchElementException | IllegalArgumentException | NullPointerException
                     | SecurityException e) {
                e.printStackTrace();
            }

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
        this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, target);

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
                if (packet instanceof ClientboundAddEntityPacket) {
                    final ClientboundAddEntityPacket entitySpawn = (ClientboundAddEntityPacket) packet;
                    final Field uuidField = entitySpawn.getClass().getDeclaredField("uuid");

                    uuidField.setAccessible(true);

                    final Player target = Bukkit.getPlayer((UUID) uuidField.get(entitySpawn));

                    if (target != null) {
                        showPlayer(player, target);
                    }
                } else if (packet instanceof ClientboundRespawnPacket) {
                    showPlayer(player, player);
                }

                super.write(context, packet, promise);
            }
        };
    }

    /**
     * Get the {@link ServerGamePacketListenerImpl} of a player
     *
     * @param player the player to get the player connection object from
     * @return the object
     */
    private ServerGamePacketListenerImpl getPlayerConnection(final Player player) {
        return ((CraftPlayer) player).getHandle().connection;
    }

    /**
     * Send the {@link ClientboundPlayerInfoUpdatePacket} to a player
     *
     * @param player the player
     * @param action the action
     * @param target the target
     */
    private void sendInfoPacket(final Player player, final ClientboundPlayerInfoUpdatePacket.Action action, final ServerPlayer target) {
        this.sendPacket(player, new ClientboundPlayerInfoUpdatePacket(action, target));
    }

    /**
     * Send the {@link ClientboundPlayerInfoUpdatePacket} to a player
     *
     * @param player the player
     * @param action the action
     * @param target the target
     */
    private void sendInfoPacket(final Player player, final ClientboundPlayerInfoUpdatePacket.Action action, final Player target) {
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
