package com.qualityplus.collections.listener;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.collections.TheCollections;
import com.qualityplus.collections.api.service.AntiExploitService;
import com.qualityplus.collections.api.service.CollectionsService;
import com.qualityplus.collections.base.collection.Collection;
import com.qualityplus.collections.base.collection.registry.CollectionsRegistry;
import com.qualityplus.collections.util.CollectionsItemStackUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@Component
public final class CollectionsMainListener implements Listener {
    private @Inject AntiExploitService antiExploitService;
    private @Inject CollectionsService collectionsService;


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onExtractFromFurnace(FurnaceExtractEvent e) {
        XMaterial pickUpMaterial = XMaterial.matchXMaterial(e.getItemType());

        Optional<Collection> collection = CollectionsRegistry.getByMaterial(pickUpMaterial);

        if(!collection.isPresent()) return;

        collectionsService.addXp(e.getPlayer(), collection.get(), e.getItemAmount());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPickUpItem(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();

        ItemStack pickUpItem = e.getItem().getItemStack();

        if (antiExploitService.hasMetadata(e.getItem())) return;

        if (isOnTimer(player, e)) return;

        XMaterial pickUpMaterial = XMaterial.matchXMaterial(pickUpItem);

        Optional<Collection> collection = CollectionsRegistry.getByMaterial(pickUpMaterial);

        if(!collection.isPresent()) return;

        antiExploitService.setPlayerTimer(player, -1);

        collectionsService.addXp(player, collection.get(), pickUpItem.getAmount());
    }

    @EventHandler(ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent e) {
        antiExploitService.addMetadata(e.getItemDrop());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakInventoryHolders(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if(antiExploitService.hasMetadata(e.getBlock())){
            addMetadataToNearEntities(player.getLocation(), 5);
        }

        if (e.getBlock().getState() instanceof org.bukkit.inventory.InventoryHolder) {
            addMetadataToNearEntities(player.getLocation(), 5);
            antiExploitService.setPlayerTimer(player, System.currentTimeMillis() + 140L);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDispenseEvent(BlockDispenseEvent e) {
        addMetadataToNearPlayers(e.getBlock().getLocation(), 3);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent e) {
        addMetadataToNearPlayers(e.getBlock().getLocation(), 3);
    }

    private void addMetadataToNearPlayers(Location location, int radius) {
        Bukkit.getScheduler().runTaskLater(TheCollections.getApi().getPlugin(), () -> Optional.ofNullable(location.getWorld())
                .ifPresent(world -> world.getNearbyEntities(location, radius,radius, radius)
                        .stream()
                        .filter(entity -> entity instanceof Player)
                        .map(entity -> (Player) entity)
                        .forEach(antiExploitService::addMetadata)), 1);
    }

    private void addMetadataToNearEntities(Location location, int radius) {
        Bukkit.getScheduler().runTaskLater(TheCollections.getApi().getPlugin(), () -> Optional.ofNullable(location.getWorld())
                .ifPresent(world -> world.getNearbyEntities(location, radius,radius, radius)
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .forEach(antiExploitService::addMetadata)), 1);
    }

    private boolean isOnTimer(Player player, Cancellable cancellable){
        if(System.currentTimeMillis() < antiExploitService.getPlayerTimer(player)){
            cancellable.setCancelled(true);
            return true;
        }else
            return false;
    }
}
