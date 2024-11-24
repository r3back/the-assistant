package com.qualityplus.assistant.base.nms;

import com.qualityplus.assistant.api.util.ReflectionUtil;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.net.SocketAddress;

/**
 * Empty connection class
 */
@SuppressWarnings("all")
public final class EmptyConnection_1_20_R4 extends Connection {
    @NotNull
    private static final MethodHandle CONNECTION_DISCONNECT_LISTENER = ReflectionUtil.getSetter(Connection.class, "p");
    @NotNull
    private static final MethodHandle CONNECTION_PACKET_LISTENER = ReflectionUtil.getSetter(Connection.class, "q");

    /**
     * Default constructor
     *
     * @param flag flag
     * @throws IOException exception
     */
    public EmptyConnection_1_20_R4(final PacketFlow flag) throws IOException {
        super(flag);
        channel = new EmptyChannel_1_20_R4(null);
        address = new SocketAddress() {
            private static final long serialVersionUID = 8207338859896320185L;
        };
    }

    /**
     * Flush channel method
     */
    @Override
    public void flushChannel() {
    }

    /**
     * Retrieves if it's connected
     */
    @Override
    public boolean isConnected() {
        return true;
    }

    /**
     * Send packet method
     *
     * @param packet packet to send
     */
    @Override
    public void send(final Packet packet) {
    }

    /**
     * Send packet with listener method
     *
     * @param packet             packet to send
     * @param packetSendListener packet listener
     */
    @Override
    public void send(final Packet packet, final PacketSendListener packetSendListener) {
    }

    /**
     * Send packet with listener and flag method
     *
     * @param packet             packet to send
     * @param packetSendListener packet listener
     * @param flag               flag
     */
    @Override
    public void send(final Packet packet, final PacketSendListener packetSendListener, final boolean flag) {
    }

    /**
     *
     * @param pl packet listener
     */
    //@Override
    public void setListener(final PacketListener pl) {
        try {
            CONNECTION_PACKET_LISTENER.invoke(this, pl);
            CONNECTION_DISCONNECT_LISTENER.invoke(this, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
