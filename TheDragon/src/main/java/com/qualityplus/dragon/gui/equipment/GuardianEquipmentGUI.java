package com.qualityplus.dragon.gui.equipment;

import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.base.game.guardian.DragonGuardian;
import com.qualityplus.dragon.base.game.guardian.GuardianArmor;
import com.qualityplus.dragon.gui.TheDragonGUI;
import com.qualityplus.dragon.gui.guardian.GuardianGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class GuardianEquipmentGUI extends TheDragonGUI {
    private final GuardianEquipmentType equipmentType;
    private final DragonGuardian guardian;
    private final ItemStack[] itemStacks;

    public GuardianEquipmentGUI(Box box, Player player, DragonGuardian guardian, GuardianEquipmentType equip) {
        super(27, "&8Select Guardian Equipment:", box);

        this.itemStacks = player.getInventory().getContents();
        this.equipmentType = equip;
        this.guardian = guardian;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Optional.ofNullable(itemStacks).ifPresent(inventory::setContents);

        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        if(!getTarget(event).equals(ClickTarget.INSIDE)) return;

        ItemStack itemStack = Optional.ofNullable(event.getCurrentItem())
                .filter(ItemStackUtils::isNotNull)
                .map(ItemStack::clone)
                .orElse(null);

        if(itemStack == null) return;

        Player player = (Player) event.getWhoClicked();

        GuardianArmor armor = guardian.getGuardianArmor();

        switch (equipmentType){
            case BOOTS:
                armor.setBoots(itemStack);
                break;
            case HELMET:
                armor.setHelmet(itemStack);
                break;
            case LEGGINGS:
                armor.setLeggings(itemStack);
                break;
            case CHESTPLATE:
                armor.setChestplate(itemStack);
                break;
        }

        player.openInventory(new GuardianGUI(box, guardian).getInventory());
    }

    public enum GuardianEquipmentType {
        HELMET,
        LEGGINGS,
        WEAPON,
        BOOTS,
        CHESTPLATE,

    }
}
