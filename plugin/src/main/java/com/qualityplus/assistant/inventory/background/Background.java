package com.qualityplus.assistant.inventory.background;

import com.qualityplus.assistant.inventory.Item;
import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Represents inventory background
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Background extends OkaeriConfig {
    private Map<Integer, Item> items;
    private Item filler;
    private boolean useFiller;

    /**
     * Default Constructor
     *
     * @param items BackGround Slots and Items
     */
    public Background(final Map<Integer, Item> items) {
        this.items = items;
    }
}
