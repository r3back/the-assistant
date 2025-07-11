package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import com.qualityplus.assistant.api.nms.tab.TabAdapter;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NMS Tab Implementation for Spigot v1_21_R5
 */
public final class v1_21_R5_Tab extends TabAdapter {
    private final Map<Player, GameProfile[]> profiles = new HashMap<>();
    private final List<Player> initialized = new ArrayList<>();
    private static final Integer MAX_SLOTS = 24;

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
        /*if (header != null || footer != null) {
            final MinecraftServer server = MinecraftServer.getServer();

            final ClientboundTabListPacket packet = new ClientboundTabListPacket(
                    Objects.requireNonNull(Component.Serializer.fromJson("{\"text\": \"" + header + "\"}", server.registryAccess())),
                    Objects.requireNonNull(Component.Serializer.fromJson("{\"text\": \"" + footer + "\"}", server.registryAccess()))
            );

            this.sendPacket(player, packet);
        }*/

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
        /*final GameProfile profile = this.profiles.get(player)[index];
        final Property property = profile.getProperties().get("textures").iterator().next();
        final ServerPlayer entityPlayer = this.getEntityPlayer(profile);

        String[] skinData = skinDataParam;
        skinData = skinData != null && skinData.length >= 1 && !skinData[0].isEmpty() && !skinData[1].isEmpty()
                ? skinData
                : SkinType.DARK_GRAY.getSkinData();

        try {
            final Method method = property.getClass().getDeclaredMethod("signature");
            final Object value = method.invoke(property);
            final String signature = (String) value;

            if (!signature.equals(skinData[1]) || !signature.equals(skinData[0])) {
                profile.getProperties().remove("textures", property);
                profile.getProperties().put("textures", new Property("textures", skinData[0], skinData[1]));

                this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, entityPlayer);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }*/


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
        /*final GameProfile profile = this.profiles.get(player)[axis];
        final ServerPlayer entityPlayer = this.getEntityPlayer(profile);
        final MinecraftServer server = MinecraftServer.getServer();

        if (text == null || text.isEmpty()) {
            entityPlayer.listName = Component.Serializer.fromJsonLenient("test nul", server.registryAccess());
        } else {
            entityPlayer.listName = Component.Serializer.fromJsonLenient(text, server.registryAccess());
        }

        try {
            final Field pingField = ServerPlayer.class.getDeclaredField("containerCounter");

            pingField.setAccessible(true);

            pingField.set(entityPlayer, ping);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }

        setFakePing(entityPlayer.getBukkitEntity());

        this.setupScoreboard(player, text, profile.getName());
        this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, entityPlayer);
*/
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
        /*if (!this.initialized.contains(player)) {
            for (int i = 0; i < MAX_SLOTS; i++) {
                final GameProfile profile = this.profiles.get(player)[i];
                final ServerPlayer entityPlayer = this.getEntityPlayer(profile);

                final ClientboundPlayerInfoUpdatePacket.Entry entry = new ClientboundPlayerInfoUpdatePacket.Entry(
                        entityPlayer.getUUID(),
                        entityPlayer.getGameProfile(),
                        true,
                        entityPlayer.connection.latency(),
                        GameType.SURVIVAL,
                        entityPlayer.getTabListDisplayName(),
                        entityPlayer.isModelPartShown(PlayerModelPart.HAT),
                        entityPlayer.getTabListOrder(),
                        Optionull.map(entityPlayer.getChatSession(), RemoteChatSession::asData)
                );

                final ClientboundPlayerInfoUpdatePacket packet = ClientboundPlayerInfoUpdatePacket
                        .createPlayerInitializing(Collections.singletonList(entityPlayer));
                try {
                    final Field field = ClientboundPlayerInfoUpdatePacket.class.getDeclaredField("entries");
                    field.setAccessible(true);
                    field.set(packet, Lists.newArrayList(entry));

                    final List<ClientboundPlayerInfoUpdatePacket.Entry> entries = (List<ClientboundPlayerInfoUpdatePacket.Entry>) field.get(packet);

                    //NMSImpl.test(packet, Lists.newArrayList(entry));
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }


                //this.sendInfoPacket(player, , entityPlayer);
                this.sendPacket(player, packet);

            }

            this.initialized.add(player);
        }
*/
        return this;
    }

    /**
     * Get an entity player by a profile
     *
     * @param profile the profile
     * @return the entity player
     */
    /*private ServerPlayer getEntityPlayer(final GameProfile profile) {
        final MinecraftServer server = MinecraftServer.getServer();

        final ServerLevel worldServer = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

        final ClientInformation clientInfo = ClientInformation.createDefault()

        return new EntityHumanNPC_1_21_R4(server, worldServer, profile, clientInfo);
    }*/

    /**
     * Set fake ping to player
     *
     * @param play {@link Player}
     */
    public void setFakePing(final Player play) {
        /*for (final Player p : Bukkit.getOnlinePlayers()) {
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

                try {
                    final Field pingField = ServerPlayer.class.getDeclaredField("containerCounter");

                    pingField.setAccessible(true);

                    final int ping = (int) pingField.get(player);

                    ob.getScore(play).setScore(ping);

                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
                    e.printStackTrace();
                }



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
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }*/
    }


    /**
     * Hide all real players from the tab
     *
     * @param player the player
     * @return the current adapter instance
     */
    @Override
    public TabAdapter hideRealPlayers(final Player player) {
        /*for (Player target : Bukkit.getOnlinePlayers()) {
            this.hidePlayer(player, target);
        }*/

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
        /*if (player.canSee(target) || target.equals(player)) {
            this.getPlayerConnection(player).send(new ClientboundPlayerInfoRemovePacket(Collections.singletonList(getHandle(target).getUUID())));
        }*/

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
        /*if (!this.initialized.contains(player)) {
            final ServerGamePacketListenerImpl connection = this.getPlayerConnection(player);

            final ServerCommonPacketListenerImpl commonPacketListener = connection;

            try {
                final Field networkField = commonPacketListener.getClass().getSuperclass().getDeclaredField("connection");

                networkField.setAccessible(true);

                final Connection manager = (Connection) networkField.get(connection);

                final ChannelPipeline pipeline = manager.channel.pipeline();

                while (pipeline.get("packet_handler") == null) {
                    this.showRealPlayers(player);
                }

                pipeline.addBefore("packet_handler", player.getName(), this.createShowListener(player));
            } catch (NoSuchFieldException | NoSuchElementException | IllegalArgumentException | NullPointerException
                     | SecurityException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }*/

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
        //this.sendInfoPacket(player, ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, target);

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
                /*if (packet instanceof ClientboundPlayerInfoUpdatePacket) {
                    final ClientboundPlayerInfoUpdatePacket entitySpawn = (ClientboundPlayerInfoUpdatePacket) packet;

                    final Field entriesField = entitySpawn.getClass().getDeclaredField("b");

                    entriesField.setAccessible(true);

                    final List<ClientboundPlayerInfoUpdatePacket.Entry> entries =
                            (List<ClientboundPlayerInfoUpdatePacket.Entry>) entriesField.get(entitySpawn);

                    final UUID uuid = entries.get(0).profileId();

                    Bukkit.getConsoleSender().sendMessage("UUID Listener: " + uuid.toString());

                    final Player target = Bukkit.getPlayer((UUID) uuid);

                    if (target != null) {
                        showPlayer(player, target);
                    }
                } else if (packet instanceof ClientboundRespawnPacket) {
                    showPlayer(player, player);
                }*/

                super.write(context, packet, promise);
            }
        };
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
        /*if (!this.profiles.containsKey(player)) {
            this.profiles.put(player, new GameProfile[MAX_SLOTS]);
        }

        if (this.profiles.get(player).length < index + 1 || this.profiles.get(player)[index] == null) {
            final GameProfile profile = new GameProfile(UUID.randomUUID(), text);
            final String[] skinData = SkinType.DARK_GRAY.getSkinData();

            profile.getProperties().put("textures", new Property("textures", skinData[0], skinData[1]));

            this.profiles.get(player)[index] = profile;
        }*/
    }
}
