package com.qualityplus.assistant.util.faster;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.inventory.ItemStack;

public final class FasterStack extends OkaeriConfig {
    public static ItemStack fast(XMaterial material, XEnchantment enchant, int level){
        ItemStack itemStack = material.parseItem();

        if(enchant == null) return itemStack;

        itemStack.addUnsafeEnchantment(enchant.parseEnchantment(), level);

        return itemStack;
    }

    public static ItemStack fast(XMaterial material, int amount){
        return new ItemStack(material.parseMaterial(), amount);
    }
}
