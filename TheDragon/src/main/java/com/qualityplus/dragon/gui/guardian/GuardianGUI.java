package com.qualityplus.dragon.gui.guardian;

import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.inventory.InventoryUtils;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.dragon.TheDragon;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.api.game.guardian.Guardian;
import com.qualityplus.dragon.gui.TheDragonGUI;
import com.qualityplus.dragon.gui.equipment.GuardianEquipmentGUI;
import com.qualityplus.dragon.gui.guardians.DragonGuardiansGUI;
import com.qualityplus.dragon.base.game.guardian.DragonGuardian;
import com.qualityplus.dragon.base.game.guardian.GuardianArmor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import com.qualityplus.dragon.base.service.SetupServiceImpl.*;

import java.util.Collections;

public final class GuardianGUI extends TheDragonGUI {
    private final GuardianGUIConfig config;
    private final DragonGuardian guardian;

    public GuardianGUI(Box box, Guardian guardian) {
        super(box.files().inventories().guardianGUIConfig, box);

        this.config = box.files().inventories().guardianGUIConfig;
        this.guardian = (DragonGuardian) guardian;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Item Editor"));

        InventoryUtils.fillInventory(inventory, config.getBackground());


        GuardianArmor guardianArmor = guardian.getGuardianArmor();

        inventory.setItem(config.getHelmetItem().slot, ItemStackUtils.makeItem(config.getHelmetItem(), guardianArmor.getHelmet()));
        inventory.setItem(config.getChestPlateItem().slot, ItemStackUtils.makeItem(config.getChestPlateItem(), guardianArmor.getChestplate()));
        inventory.setItem(config.getLeggingsItem().slot, ItemStackUtils.makeItem(config.getLeggingsItem(), guardianArmor.getLeggings()));
        inventory.setItem(config.getBootsItem().slot, ItemStackUtils.makeItem(config.getBootsItem(), guardianArmor.getBoots()));
        inventory.setItem(config.getMobTypeItem().slot, ItemStackUtils.makeItem(config.getMobTypeItem(), Collections.singletonList(new Placeholder("type", String.valueOf(guardian.getEntity())))));
        inventory.setItem(config.getDisplayNameItem().slot, ItemStackUtils.makeItem(config.getDisplayNameItem(), Collections.singletonList(new Placeholder("displayname", String.valueOf(guardian.getDisplayName())))));
        inventory.setItem(config.getHealthItem().slot, ItemStackUtils.makeItem(config.getHealthItem(), Collections.singletonList(new Placeholder("health", String.valueOf(guardian.getHealth())))));

        inventory.setItem(config.getGoBackItem().slot, ItemStackUtils.makeItem(config.getGoBackItem()));
        inventory.setItem(config.getCloseGUI().slot, ItemStackUtils.makeItem(config.getCloseGUI()));

        return inventory;
    }
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        int slot = e.getSlot();

        e.setCancelled(true);

        if(!getTarget(e).equals(ClickTarget.INSIDE)) return;

        if(isItem(slot, config.getCloseGUI())) {
            player.closeInventory();
        }else if (isItem(slot, config.getGoBackItem())){
            player.openInventory(new DragonGuardiansGUI(box, 1).getInventory());
        }else if (isItem(slot, config.getHelmetItem())){
            player.openInventory(new GuardianEquipmentGUI(box, player, guardian, GuardianEquipmentGUI.GuardianEquipmentType.HELMET).getInventory());
        }else if (isItem(slot, config.getChestPlateItem())){
            player.openInventory(new GuardianEquipmentGUI(box, player, guardian, GuardianEquipmentGUI.GuardianEquipmentType.CHESTPLATE).getInventory());
        }else if (isItem(slot, config.getLeggingsItem())){
            player.openInventory(new GuardianEquipmentGUI(box, player, guardian, GuardianEquipmentGUI.GuardianEquipmentType.LEGGINGS).getInventory());
        }else if (isItem(slot, config.getBootsItem())){
            player.openInventory(new GuardianEquipmentGUI(box, player, guardian, GuardianEquipmentGUI.GuardianEquipmentType.BOOTS).getInventory());
        }else
            setSetupMode(player, slot);
    }

    private void setSetupMode(Player player, int slot){

        EditType type = slot == config.getMobTypeItem().slot ? EditType.MOB :
                slot == config.getDisplayNameItem().slot ? EditType.DISPLAYNAME :
                        slot == config.getHealthItem().slot ? EditType.HEALTH : null;

        if(type == null) return;

        TheDragon.getApi().getSetupService().setInSetupMode(player.getUniqueId(), guardian, type);

        player.sendMessage(StringUtils.color(box.files().messages().setupMessages.setupModeMessage.replace("%type%", type.getName())));

        player.closeInventory();

    }

}
