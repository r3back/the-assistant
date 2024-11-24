package com.qualityplus.assistant.base.nms;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

/**
 * Empty packet listener
 */
public class EmptyPacketListener_1_21_R2 extends ServerGamePacketListenerImpl {
    /**
     * Default constructor
     *
     * @param minecraftServer {@link MinecraftServer}
     * @param networkManager  {@link MinecraftServer}
     * @param entityPlayer    {@link MinecraftServer}
     * @param clc             {@link MinecraftServer}
     */
    public EmptyPacketListener_1_21_R2(final MinecraftServer minecraftServer, final Connection networkManager, final ServerPlayer entityPlayer,
                                       final CommonListenerCookie clc) {
        super(minecraftServer, networkManager, entityPlayer, clc);
    }

    /**
     * Resume flushing
     */
    @Override
    public void resumeFlushing() {
    }

    /**
     * Send packet method
     *
     * @param packet packet to send
     */
    @Override
    public void send(final Packet<?> packet) {
    }
}
