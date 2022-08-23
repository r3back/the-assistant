package com.qualityplus.collections.base.collection.gui;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import lombok.*;

import java.util.List;

@NoArgsConstructor @Setter @Getter
public final class GUIOptions extends OkaeriConfig {
    private int slot;
    private int page;
    private XMaterial item;
    private String texture;
    private List<String> mainMenuLore;

    @Builder
    public GUIOptions(int slot, int page, XMaterial item, String texture, List<String> mainMenuLore) {
        this.slot = slot;
        this.page = page;
        this.item = item;
        this.texture = texture;
        this.mainMenuLore = mainMenuLore;
    }
}
