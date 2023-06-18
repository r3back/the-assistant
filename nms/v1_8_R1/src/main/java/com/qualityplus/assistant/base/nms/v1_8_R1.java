package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import com.qualityplus.assistant.api.gui.FakeInventory;
import com.qualityplus.assistant.api.gui.fake.FakeInventoryImpl;
import com.qualityplus.assistant.api.util.FakeInventoryFactory;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.*;

/**
 * NMS Implementation for Spigot v1_8_R1
 */
public final class v1_8_R1 extends AbstractNMS {
    private @Getter @Inject Plugin plugin;

    @Override
    public void setBlockAge(final Block block, final int age) {
        final CraftBlock craftBlock = (((CraftBlock) block));
        craftBlock.setData((byte)age);
    }

    @Override
    public int getAge(final Block block) {
        final CraftBlock craftBlock = (((CraftBlock) block));
        return craftBlock.getData();
    }

    @Override
    public void damageBlock(final List<Player> players, final Block block, int damage) {
        final int x = block.getX();
        final int y = block.getY();
        final int z = block.getZ();

        final BlockPosition position = new BlockPosition(x, y, z);

        //Keeps the same id to prevent packet glitch
        final Integer id = Optional.ofNullable(clickCache.getIfPresent(block))
                .orElse(new Random().nextInt(2000));

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
        final Location location = new Location(playerWorld, 0,0,0);
        final MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        final WorldServer worldServer = ((CraftWorld) playerWorld).getHandle();
        final EntityPlayer fakePlayer = new EntityPlayer(minecraftServer, worldServer,
                new GameProfile(UUID.randomUUID(), name),
                new PlayerInteractManager(worldServer));
        fakePlayer.getBukkitEntity().setMetadata("NPC", new FixedMetadataValue(plugin, "UUID"));
        fakePlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        fakePlayer.playerConnection = new PlayerConnection(minecraftServer, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), fakePlayer);
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
        final EntityComplexPart part = dragonPart.equals(DragonPart.HEAD) ?
                ((CraftEnderDragon) enderDragon).getHandle().bn :
                ((CraftEnderDragon) enderDragon).getHandle().bp;
        return new Location(enderDragon.getWorld(), part.lastX, part.lastY, part.lastZ);
    }

    @Override
    public void sendBossBar(final Player player, final String message) {
        if (message == null || message.equals("")) {
            BossBarAPI.removeAllBars(player);
            return;
        }
        BossBarAPI.removeBar(player);
        BossBarAPI.addBar(player,  new TextComponent(message),
                BossBarAPI.Color.PURPLE,
                BossBarAPI.Style.NOTCHED_10, 10,
                BossBarAPI.Property.DARKEN_SKY);
    }

    @Override
    public void setEnderEye(final Block block, final boolean setEnderEye) {
        block.getState().setRawData(setEnderEye ? (byte) 4 : (byte) 3);
    }
}
