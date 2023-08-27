package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import com.qualityplus.assistant.api.gui.FakeInventory;
import com.qualityplus.assistant.api.gui.fake.FakeInventoryImpl;
import com.qualityplus.assistant.api.util.FakeInventoryFactory;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.DedicatedPlayerList;
import net.minecraft.server.v1_14_R1.EntityComplexPart;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EnumProtocolDirection;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.NetworkManager;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_14_R1.PlayerConnection;
import net.minecraft.server.v1_14_R1.PlayerInteractManager;
import net.minecraft.server.v1_14_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * NMS Implementation for Spigot v1_14_R1
 */
public final class v1_14_R1 extends AbstractNMS {
    private @Getter @Inject Plugin plugin;

    @Override
    public void setBlockAge(final Block block, final int age) {
        if (block.getBlockData() instanceof Ageable) {
            final Ageable crop = (Ageable) block.getBlockData();
            crop.setAge(age);
            block.setBlockData(crop);
        }
    }

    @Override
    public int getAge(final Block block) {
        if (block.getBlockData() instanceof Ageable) {
            final Ageable crop = (Ageable) block.getBlockData();
            return crop.getAge();
        }
        return 0;
    }

    @Override
    public int getMaxAge(final Block block) {
        if (block.getBlockData() instanceof Ageable) {
            final Ageable crop = (Ageable) block.getBlockData();
            return crop.getMaximumAge();
        }
        return 0;
    }

    @Override
    public void damageBlock(final List<Player> players, final Block block, final int damage) {
        final int x = block.getX();
        final int y = block.getY();
        final int z = block.getZ();

        final BlockPosition position = new BlockPosition(x, y, z);

        //Keeps the same id to prevent packet glitch
        final Integer id = Optional.ofNullable(clickCache.getIfPresent(block)).orElse(new Random().nextInt(2000));

        clickCache.put(block, id);

        final PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(id, position, damage);

        players.stream()
                .filter(Objects::nonNull)
                .forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));
    }

    @Override
    public void damageBlock(final Player player, final Block block, final int damage) {
        damageBlock(Collections.singletonList(player), block, damage);
    }

    @Override
    public void removeFakePlayer(final FakeInventory fakeInventory) {

    }

    @Override
    public InventoryView createWorkBench(final Player player) {
        final EntityPlayer entityPlayer = getFakePlayer("Fake Inventory");

        return entityPlayer.getBukkitEntity().openWorkbench(entityPlayer.getBukkitEntity().getLocation(), true);
    }

    @Override
    public FakeInventory getFakeInventory(final Player player, final FakeInventory fakeInventory) {
        final int maxSlots = fakeInventory.getSlots();

        final ItemStack[] itemStacks = fakeInventory.getInventory().getContents().clone();

        final Inventory inventory = FakeInventoryFactory.getInventoryWithSize(itemStacks, maxSlots);

        return getInventory(inventory, maxSlots);
    }


    @Override
    public FakeInventory getFakeInventory(final Player player, final int maxSlots) {
        final Inventory inventory = FakeInventoryFactory.getInventoryWithSize(maxSlots);
        return getInventory(inventory, maxSlots);
    }

    private FakeInventory getInventory(final Inventory inventory, final int maxSlots) {
        return new FakeInventoryImpl(inventory, maxSlots);
    }

    private EntityPlayer getFakePlayer(final String name) {
        final World playerWorld = Bukkit.getWorlds().get(0);
        final Location location = new Location(playerWorld, 0, 0, 0);
        final MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        final WorldServer worldServer = ((CraftWorld) playerWorld).getHandle();
        final EntityPlayer fakePlayer = new EntityPlayer(minecraftServer, worldServer,
                new GameProfile(UUID.randomUUID(), name),
                new PlayerInteractManager(worldServer));
        fakePlayer.getBukkitEntity().setMetadata("NPC", new FixedMetadataValue(this.plugin, "UUID"));
        fakePlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        fakePlayer.playerConnection = new PlayerConnection(minecraftServer,
                new NetworkManager(EnumProtocolDirection.CLIENTBOUND), fakePlayer);
        worldServer.addEntity(fakePlayer);
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(fakePlayer.getBukkitEntity()));
        return fakePlayer;
    }

    @Override
    public ItemStack setDurability(final ItemStack itemStack, final short durability) {
        return null;
    }

    @Override
    public Location getDragonPart(final EnderDragon enderDragon, final DragonPart dragonPart) {
        final EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().bA;
        return new Location(enderDragon.getWorld(), part.lastX, dragonPart.equals(DragonPart.HEAD) ?
                part.lastY : part.lastY - DragonPart.BODY.getNmsDistance(), part.lastZ);
    }

    @Override
    public void sendBossBar(final Player player, final String message) {
        if (player == null || message == null || message.equals("")) {
            bossBar.removeAll();
            return;
        }
        Optional.ofNullable(bossBar).ifPresent(BossBar::removeAll);

        bossBar = Bukkit.createBossBar(message, BarColor.PURPLE, BarStyle.SEGMENTED_10, BarFlag.DARKEN_SKY);

        bossBar.addPlayer(player);
    }

    @Override
    public void setEnderEye(final Block block, final boolean setEnderEye) {
        if (!(block.getBlockData() instanceof EndPortalFrame)) {
            return;
        }

        final EndPortalFrame altar = (EndPortalFrame) block.getBlockData();

        altar.setEye(setEnderEye);

        block.setBlockData(altar);
    }

    @Override
    public void respawnPlayer(final Player player) {
        final DedicatedPlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        playerList.moveToWorld(entityPlayer, entityPlayer.dimension, false);
    }

    @Override
    public ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            public @NotNull ChunkData generateChunkData(final @NotNull World world, final @NotNull Random random, final int x,
                                                        final int z, final @NotNull BiomeGrid chunkGererator) {
                final ChunkData chunkData = createChunkData(world);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        chunkGererator.setBiome(i, j, Biome.THE_VOID);
                    }
                }
                return chunkData;
            }
        };
    }

    @Override
    public void setMaxHealth(final Player player, final int maxHealth) {
        Optional.ofNullable(player.getAttribute(Attribute.GENERIC_MAX_HEALTH))
                .ifPresent(attribute -> attribute.setBaseValue(maxHealth));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void setGameRule(final World world, final String key, final T value) {
        try {
            final GameRule<T> gameRule = (GameRule<T>) GameRule.getByName(key);

            if (gameRule != null && gameRule.getType().equals(value.getClass())) {
                world.setGameRule(gameRule, value);
            }
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendParticles(final World world, final String type, final float x,
                              final float y, final float z, final float offsetX,
                              final float offsetY, final float offsetZ,
                              final float data, final int amount) {
        final Particle particle = Particle.valueOf(type);
        for (final Player player : world.getPlayers()) {
            player.spawnParticle(particle, x, y, z, amount, offsetX, offsetY, offsetZ, data);
        }
    }
}
