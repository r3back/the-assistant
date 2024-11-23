package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.craftbukkit.v1_20_R4.entity.CraftPlayer;
import java.io.IOException;

/**
 * Entity human npc
 */
public final class EntityHumanNPC_1_20_R6 extends ServerPlayer {
    /**
     * Default constructor
     *
     * @param minecraftServer {@link MinecraftServer}
     * @param world           {@link ServerLevel}
     * @param gameProfile     {@link GameProfile}
     * @param ci              {@link ClientInformation}
     */
    public EntityHumanNPC_1_20_R6(final MinecraftServer minecraftServer, final ServerLevel world, final GameProfile gameProfile,
                                  final ClientInformation ci) {
        super(minecraftServer, world, gameProfile, ci);
        initialise(minecraftServer, ci);
    }

    /**
     *
     * @param f             f
     * @param f1            f1
     * @param damageSource {@link DamageSource}
     * @return if cause fall damage
     */
    @Override
    public boolean causeFallDamage(final float f, final float f1, final DamageSource damageSource) {
        return false;
    }

    /**
     *
     * @param d0          d0
     * @param flag        flag
     * @param blockState {@link BlockState}
     * @param blockPos   {@link BlockPos}
     */
    @Override
    protected void checkFallDamage(final double d0, final boolean flag, final BlockState blockState, final BlockPos blockPos) {

    }

    /**
     *
     * @param damageSource {@link DamageSource}
     */
    @Override
    public void die(final DamageSource damageSource) {
        if (dead) {
            return;
        }
        super.die(damageSource);
    }

    /**
     *
     */
    @Override
    public void doTick() {
        super.doTick();
    }

    /**
     *
     * @return {@link CraftPlayer}
     */
    @Override
    public CraftPlayer getBukkitEntity() {
        return super.getBukkitEntity();
    }

    /**
     *
     * @return {@link SoundEvent}
     */
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    /**
     *
     * @param damageSource {@link DamageSource}
     * @return {@link SoundEvent}
     */
    @Override
    protected SoundEvent getHurtSound(final DamageSource damageSource) {
        return super.getHurtSound(damageSource);
    }

    /**
     *
     * @return {@link ServerStatsCounter}
     */
    @Override
    public ServerStatsCounter getStats() {
        return super.getStats();
    }

    /**
     *
     * @return {@link Component}
     */
    @Override
    public Component getTabListDisplayName() {
        return Component.empty();
    }

    /**
     *
     * @param damageSource {@link DamageSource}
     * @param f            v
     * @return if hurt was done
     */
    @Override
    public boolean hurt(final DamageSource damageSource, final float f) {
        return super.hurt(damageSource, f);
    }

    /**
     * Init method
     *
     * @param minecraftServer {@link MinecraftServer}
     * @param clientInfo      {@link ClientInformation}
     */
    private void initialise(final MinecraftServer minecraftServer, final ClientInformation clientInfo) {
        try {
            final EmptyConnection_1_20_R6 conn = new EmptyConnection_1_20_R6(PacketFlow.CLIENTBOUND);
            connection = new EmptyPacketListener_1_20_R6(minecraftServer, conn, this,
                    new CommonListenerCookie(super.getGameProfile(), 0, clientInfo, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.invulnerableTime = 0;
    }

    /**
     *
     * @return if it's in wall
     */
    @Override
    public boolean isInWall() {
        return super.isInWall();
    }

    /**
     *
     * @return if it's pushable
     */
    @Override
    public boolean isPushable() {
        return super.isPushable();
    }

    /**
     *
     * @param strength strength
     * @param dx       dx
     * @param dz       dz
     */
    @Override
    public void knockback(final double strength, final double dx, final double dz) {
        super.knockback(strength, dx, dz);
    }


    /**
     *
     * @return if it's climbable
     */
    @Override
    public boolean onClimbable() {
        return super.onClimbable();
    }

    /**
     *
     * @param entity {@link Entity}
     */
    @Override
    public void push(final Entity entity) {
        super.push(entity);
    }

    /**
     *
     * @param reason {@link net.minecraft.world.entity.Entity.RemovalReason}
     */
    @Override
    public void remove(final RemovalReason reason) {
        super.remove(reason);
        getAdvancements().save();
    }

    /**
     *
     */
    @Override
    public void tick() {
        super.tick();
    }
}
