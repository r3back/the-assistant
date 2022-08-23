package com.qualityplus.collections.base.collection.executor;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public final class CollectionExecutor extends OkaeriConfig {
    private final ExecutorType executorType;
    private final XMaterial material;
    private final String itemStack;

    private CollectionExecutor(ExecutorType executorType, XMaterial material, String itemStack) {
        this.executorType = executorType;
        this.material = material;
        this.itemStack = itemStack;
    }

    public ItemStack getItem(){
        return ItemStackUtils.deserialize(itemStack);
    }

    public static CollectionExecutor ofMaterial(XMaterial material){
        return new CollectionExecutor(ExecutorType.BY_PICK_UP_MATERIAL, material, null);
    }

    public static CollectionExecutor ofItemStack(ItemStack itemStack){
        return new CollectionExecutor(ExecutorType.BY_PICK_UP_ITEM_STACK, null, ItemStackUtils.serialize(itemStack));
    }
}
