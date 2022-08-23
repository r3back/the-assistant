package com.qualityplus.dragon.listener.altars;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.api.service.SetupService;
import com.qualityplus.dragon.util.DragonItemStackUtil;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.qualityplus.dragon.api.game.structure.type.DragonAltar;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles the Process to start the game
 */
public final class AltarListener implements Listener {
    private @Inject SetupService setupService;
    private @Inject Box box;

    @EventHandler
    public void onUseTool(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof EnderCrystal)) return;
        ItemStack itemStack = e.getPlayer().getItemInHand();
        if(itemStack == null || itemStack.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(itemStack);
        if(!nbtItem.hasKey("dragonTool")) return;
        e.getRightClicked().remove();
    }

    /**
     * Listen for players who click in Altars
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack itemStack = player.getItemInHand();

        if(!DragonItemStackUtil.isEnderKey(itemStack)) return;

        event.setCancelled(true);

        if(box.game().isActive()){
            player.sendMessage(StringUtils.color(box.files().messages().setupMessages.errorInProgress.replace("%prefix%", box.files().config().prefix)));
            return;
        }

        if(setupService.getPlayersInSetupMode().contains(uuid)){
            player.sendMessage(StringUtils.color(box.files().messages().setupMessages.errorEditor.replace("%prefix%", box.files().config().prefix)));
            return;
        }

        manageAltars(player, event, itemStack);
    }

    /**
     * Handles the process when a player put a eye in
     * an altar
     *
     * @param player Player
     * @param event PlayerInteractEvent
     * @param itemStack ItemStack in player's hand
     */
    private void manageAltars(Player player, PlayerInteractEvent event, ItemStack itemStack) {
        Block block = event.getClickedBlock();

        if(block == null) return;

        Optional<DragonAltar> dragonAltar = box.structures().getAltar(block.getLocation());

        if (!dragonAltar.isPresent()) return;

        if (dragonAltar.get().isInUse()) {
            player.sendMessage(StringUtils.color(box.files().messages().setupMessages.alreadyPlaced.replace("%prefix%", box.files().config().prefix)));
            return;
        }

        if(itemStack.getAmount() == 1) {
            player.setItemInHand(null);
        }else {
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.setItemInHand(itemStack);
        }

        dragonAltar.get().setInUse(true);
        List<DragonAltar> altarList = box.structures().getAltars();

        int current = (int) altarList.stream().filter(DragonAltar::isInUse).count();
        int total = altarList.size();

        List<IPlaceholder> placeholders = Arrays.asList(new Placeholder("current", current)
                , new Placeholder("total", total)
                , new Placeholder("prefix", box.files().config().prefix));

        player.sendMessage(StringUtils.color(StringUtils.processMulti(box.files().messages().setupMessages.placedEye, placeholders)));

        if(current < total) return;

        altarList.forEach(dragonAltar1 -> dragonAltar1.setInUse(false));

        player.sendMessage(StringUtils.color(StringUtils.processMulti(box.files().messages().setupMessages.ritualCompleted, placeholders)));
        box.game().start();

    }
}