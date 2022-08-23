package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class v1_14_R1 extends AbstractNMS{
    private @Getter @Inject Plugin plugin;

    @Override
    public InventoryView getFakeInventory(Player player) {
        World playerWorld = player.getWorld();
        Location location = player.getLocation();
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) playerWorld).getHandle();
        EntityPlayer fakePlayer = new EntityPlayer(minecraftServer, worldServer, new GameProfile(UUID.randomUUID(), "Fake Inventory"), new PlayerInteractManager(worldServer));
        fakePlayer.getBukkitEntity().setMetadata("NPC", new FixedMetadataValue(plugin, "UUID"));
        fakePlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        fakePlayer.playerConnection = new PlayerConnection(minecraftServer, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), fakePlayer);
        worldServer.addEntity(fakePlayer);
        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(fakePlayer));
        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(fakePlayer, (byte) (int) (location.getYaw() * 256.0F / 360.0F)));
        player.hidePlayer(fakePlayer.getBukkitEntity());
        return fakePlayer.getBukkitEntity().openWorkbench(fakePlayer.getBukkitEntity().getLocation(), true);
    }

    @Override
    public ItemStack setDurability(ItemStack itemStack, short durability) {
        return null;
    }

    @Override
    public Location getDragonPart(EnderDragon enderDragon, DragonPart dragonPart) {
        EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().bA ;
        return new Location(enderDragon.getWorld(), part.lastX, dragonPart.equals(DragonPart.HEAD) ? part.lastY : part.lastY - DragonPart.BODY.nmsDistance, part.lastZ);
    }

    @Override
    public void sendBossBar(Player player, String bossBar) {

    }
}
