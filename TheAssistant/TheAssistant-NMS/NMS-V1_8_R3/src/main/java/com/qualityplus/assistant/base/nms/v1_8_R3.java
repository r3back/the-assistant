package com.qualityplus.assistant.base.nms;

import com.mojang.authlib.GameProfile;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public final class v1_8_R3 extends AbstractNMS{
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
        EntityComplexPart part = dragonPart.equals(DragonPart.HEAD) ? ((CraftEnderDragon) enderDragon).getHandle().bn : ((CraftEnderDragon) enderDragon).getHandle().bp;
        return new Location(enderDragon.getWorld(), part.lastX, part.lastY, part.lastZ);
    }

    @Override
    public void sendBossBar(Player player, String bossBar) {

    }
}
