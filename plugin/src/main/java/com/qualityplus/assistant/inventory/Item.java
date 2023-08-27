package com.qualityplus.assistant.inventory;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Item for guis
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Item extends OkaeriConfig {
    private XMaterial material;
    private int amount;
    private String displayName;
    private String headData;
    private String headOwner;
    private UUID headOwnerUUID;
    private List<String> lore;
    private Integer slot;
    private boolean enabled;
    private String command;
    private boolean enchanted = false;
    private Integer customModelData;

    /**
     * Copy constructor
     *
     * @param item {@link Item} item to be copied
     */
    public Item(final Item item) {
        this.material = item.material;
        this.amount = item.amount;
        this.displayName = item.displayName;
        this.headData = item.headData;
        this.headOwner = item.headOwner;
        this.headOwnerUUID = item.headOwnerUUID;
        this.lore = item.lore;
        this.slot = item.slot;
        this.enabled = item.enabled;
        this.command = item.command;
        this.enchanted = item.enchanted;
        this.customModelData = item.customModelData;
    }
}
