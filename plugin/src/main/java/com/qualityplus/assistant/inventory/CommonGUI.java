package com.qualityplus.assistant.inventory;

import com.qualityplus.assistant.inventory.background.Background;
import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common GUI
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class CommonGUI extends OkaeriConfig {
    private String title;
    private int size;
    private Background background;
    private Item closeGUI;
}
