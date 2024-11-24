package com.qualityplus.assistant.base.nms;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;

import java.net.SocketAddress;

/**
 * Empty channel class
 */
public final class EmptyChannel_1_21_R2 extends AbstractChannel {
    private final ChannelConfig config = new DefaultChannelConfig(this);

    /**
     *
     * @param parent  {@link Channel}
     */
    public EmptyChannel_1_21_R2(final Channel parent) {
        super(parent);
    }

    /**
     *
     * @return {@link ChannelConfig}
     */
    @Override
    public ChannelConfig config() {
        this.config.setAutoRead(true);
        return this.config;
    }

    /**
     *
     * @throws Exception thrown exception
     */
    @Override
    protected void doBeginRead() throws Exception {
    }

    /**
     *
     * @param arg0 {@link SocketAddress}
     * @throws Exception thrown exception
     */
    @Override
    protected void doBind(final SocketAddress arg0) throws Exception {
    }

    /**
     *
     * @throws Exception thrown exception
     */
    @Override
    protected void doClose() throws Exception {
    }

    /**
     *
     * @throws Exception thrown exception
     */
    @Override
    protected void doDisconnect() throws Exception {
    }

    /**
     *
     * @param arg0 {@link ChannelOutboundBuffer}
     * @throws Exception thrown exception
     */
    @Override
    protected void doWrite(final ChannelOutboundBuffer arg0) throws Exception {
    }

    /**
     *
     * @return if it's active
     */
    @Override
    public boolean isActive() {
        return false;
    }

    /**
     *
     * @param arg0 {@link EventLoop}
     * @return t
     */
    @Override
    protected boolean isCompatible(final EventLoop arg0) {
        return false;
    }

    /**
     *
     * @return if it's open
     */
    @Override
    public boolean isOpen() {
        return false;
    }

    /**
     *
     * @return {@link SocketAddress}
     */
    @Override
    protected SocketAddress localAddress0() {
        return null;
    }

    /**
     *
     * @return {@link ChannelMetadata}
     */
    @Override
    public ChannelMetadata metadata() {
        return new ChannelMetadata(true);
    }

    /**
     *
     * @return {@link AbstractUnsafe}
     */
    @Override
    protected AbstractUnsafe newUnsafe() {
        return null;
    }

    /**
     *
     * @return {@link SocketAddress}
     */
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
}
