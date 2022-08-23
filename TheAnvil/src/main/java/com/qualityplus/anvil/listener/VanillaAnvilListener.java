package com.qualityplus.anvil.listener;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.anvil.base.gui.anvilmain.AnvilMainGUI;
import com.qualityplus.anvil.api.box.Box;
import com.qualityplus.anvil.base.session.AnvilSessionImpl;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@Component
public final class VanillaAnvilListener implements Listener {
    private @Inject Box box;

    @EventHandler
    public void onOpenBrewingStand(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block bl = e.getClickedBlock();
        if(bl == null || bl.getType() != XMaterial.ANVIL.parseMaterial()) return;

        if(!box.files().config().openAsVanillaAnvil) return;

        e.setCancelled(true);
        e.getPlayer().openInventory(new AnvilMainGUI(box, new AnvilSessionImpl(null, null, null)).getInventory());
    }
}
