package com.qualityplus.assistant.okaeri.serdes.itemstack;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;

public final class CraftItemStackSerializer implements ObjectSerializer<ItemStack> {
    private static final Yaml YAML = new Yaml();

    @Override
    public boolean supports(final @NonNull Class<? super ItemStack> type) {
        return ItemStack.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(final @NonNull ItemStack stack, final @NonNull SerializationData data, final @NonNull GenericsDeclaration generics) {

        final YamlConfiguration craftConfig = new YamlConfiguration();
        craftConfig.set("_", stack);

        final Map<String, Map<String, Object>> root = YAML.load(craftConfig.saveToString());
        final Map<String, Object> itemMap = root.get("_");

        itemMap.remove("==");
        itemMap.forEach(data::add);
    }

    @Override
    @SneakyThrows
    public ItemStack deserialize(final @NonNull DeserializationData data, final @NonNull GenericsDeclaration generics) {

        final Map<String, Object> itemMap = new LinkedHashMap<>();
        itemMap.put("==", "org.bukkit.inventory.ItemStack");
        itemMap.putAll(data.asMap());

        final YamlConfiguration craftConfig = new YamlConfiguration();
        craftConfig.set("_", itemMap);
        craftConfig.loadFromString(craftConfig.saveToString());

        return craftConfig.getItemStack("_");
    }
}