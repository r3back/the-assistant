package com.qualityplus.collections.util;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.inventory.Item;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.itemstack.ItemBuilder;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.collections.TheCollections;
import com.qualityplus.collections.base.collection.gui.GUIOptions;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

import java.util.Collections;
import java.util.List;

@UtilityClass
public final class CollectionsItemStackUtil {
    public static ItemStack makeItem(Item item, List<IPlaceholder> placeholders, GUIOptions skillGUIOptions){
        try {
            Item item1 = ItemBuilder.of(skillGUIOptions.getItem(), 1, 1, "", Collections.emptyList()).headData(skillGUIOptions.getTexture()).build();

            ItemStack firstProcess = ItemStackUtils.makeItem(
                    skillGUIOptions.getItem(),
                    1,
                    StringUtils.processMulti(item.displayName, placeholders),
                    StringUtils.processMulti(item.lore, placeholders));

            return ItemStackUtils.getFinalItem(item1, firstProcess, placeholders);
        } catch (Exception e) {
            e.printStackTrace();
            return XMaterial.STONE.parseItem();
        }
    }
}
