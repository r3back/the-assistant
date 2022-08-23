package com.qualityplus.enchanting.util;

import com.qualityplus.assistant.util.placeholder.PlaceholderBuilder;
import com.qualityplus.enchanting.api.enchantment.ICoreEnchantment;
import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class EnchantingPlaceholderUtils {

    public PlaceholderBuilder getEnchantBuilder(ICoreEnchantment enchantment){
        return PlaceholderBuilder.create(
                new Placeholder("enchanting_enchant_displayname", enchantment.getName()),
                new Placeholder("enchanting_enchant_description", enchantment.getDescription()),
                new Placeholder("enchanting_enchant_required_bookshelf", enchantment.getRequiredBookShelf())
        );
    }

    public List<IPlaceholder> getEnchantPlaceholders(ICoreEnchantment enchantment){
        return getEnchantBuilder(enchantment).get();
    }

    private String emptyIfNull(String input){
        return input == null || input.equals("") ? "&cEmpty" : input;
    }
}
