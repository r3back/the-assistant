package com.qualityplus.assistant.inventory;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.qualityplus.assistant.api.event.SignCompletedEvent;
import com.qualityplus.assistant.api.sign.SignGUI;
import com.qualityplus.assistant.api.sign.handler.SignCompleteHandler;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sign GUI implementation
 */
public final class SignGUIImpl implements SignGUI {
    private final SignCompleteHandler action;
    private PacketAdapter packetListener;
    private final List<String> lines;
    private LeaveListener listener;
    private final Plugin plugin;
    private final UUID uuid;
    private Sign sign;

    /**
     * Default Constructor
     *
     * @param action    {@link SignCompleteHandler}
     * @param withLines initial sign lines
     * @param uuid      {@link UUID} player uuid
     * @param plugin    {@link Plugin} instance
     */
    @Builder
    public SignGUIImpl(final SignCompleteHandler action, final List<String> withLines,
                       final UUID uuid, final Plugin plugin) {
        this.lines = withLines;
        this.plugin = plugin;
        this.action = action;
        this.uuid = uuid;
    }

    @Override
    public void open() {
        final Player player = Bukkit.getPlayer(this.uuid);

        if (player == null) {
            return;
        }

        this.listener = new LeaveListener();

        final int x_start = player.getLocation().getBlockX();

        int y_start = 255;

        final int z_start = player.getLocation().getBlockZ();

        Material material = Material.getMaterial("WALL_SIGN");

        if (material == null) {
            material = Material.OAK_WALL_SIGN;
        }

        while (!player.getWorld().getBlockAt(x_start, y_start, z_start).getType().equals(Material.AIR) &&
                !player.getWorld().getBlockAt(x_start, y_start, z_start).getType().equals(material)) {
            y_start--;
            if (y_start == 1) {
                return;
            }
        }
        player.getWorld().getBlockAt(x_start, y_start, z_start).setType(material);

        this.sign = (Sign) player.getWorld().getBlockAt(x_start, y_start, z_start).getState();

        int i = 0;
        for (String line : this.lines) {
            this.sign.setLine(i, line);
            i++;
        }

        this.sign.update(false, false);


        final PacketContainer openSign = ProtocolLibrary.getProtocolManager().createPacket(
                PacketType.Play.Server.OPEN_SIGN_EDITOR
        );

        final BlockPosition position = new BlockPosition(x_start, y_start, z_start);

        openSign.getBlockPositionModifier().write(0, position);

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, openSign);
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
            }
        }, 3L);

        Bukkit.getPluginManager().registerEvents(this.listener, this.plugin);
        registerSignUpdateListener();
    }

    /**
     * Class to handle when a player leaves server
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private class LeaveListener implements Listener {
        /**
         * Listener handler
         *
         * @param e {@link PlayerQuitEvent}
         */
        @EventHandler
        public void onLeave(final PlayerQuitEvent e) {
            if (!e.getPlayer().getUniqueId().equals(SignGUIImpl.this.uuid)) {
                return;
            }
            ProtocolLibrary.getProtocolManager().removePacketListener(SignGUIImpl.this.packetListener);
            HandlerList.unregisterAll(this);
            SignGUIImpl.this.sign.getBlock().setType(Material.AIR);
        }
    }

    private void registerSignUpdateListener() {
        final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        this.packetListener = new PacketAdapter(this.plugin, PacketType.Play.Client.UPDATE_SIGN) {
            public void onPacketReceiving(final PacketEvent event) {
                if (event.getPlayer().getUniqueId().equals(SignGUIImpl.this.uuid)) {
                    final List<String> lines = Stream.of(0, 1, 2, 3)
                            .map(line -> getLine(event, line))
                            .collect(Collectors.toList());

                    Bukkit.getScheduler().runTask(this.plugin, () -> {
                        manager.removePacketListener(this);

                        HandlerList.unregisterAll(SignGUIImpl.this.listener);

                        SignGUIImpl.this.sign.getBlock().setType(Material.AIR);

                        SignGUIImpl.this.action.onSignClose(new SignCompletedEvent(event.getPlayer(), lines));
                    });
                }
            }
        };
        manager.addPacketListener(this.packetListener);
    }


    private String getLine(final PacketEvent event, final int line) {
        return Bukkit.getVersion().contains("1.8") ? getLineIn1_8Version(event, line) : getLineInNewestVersion(event, line);
    }

    private String getLineIn1_8Version(final PacketEvent event, final int line) {
        final PacketContainer container = event.getPacket();

        final WrappedChatComponent[] components = container
                .getChatComponentArrays()
                .read(0);

        return components[line]
                .getJson()
                .replaceAll("\"", "");
    }

    private String getLineInNewestVersion(final PacketEvent event, final int line) {
        final String[] lines = event.getPacket().getStringArrays().read(0);

        return lines[line];
    }
}
