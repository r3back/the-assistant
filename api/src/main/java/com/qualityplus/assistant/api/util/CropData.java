package com.qualityplus.assistant.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Used to Represent max crop age
 */
@Getter
@AllArgsConstructor
public enum CropData {
    /**
     * Wheat Crop
     */
    WHEAT(7),
    /**
     * Carrot Crop
     */
    CARROTS(7),
    /**
     * Potato Crop
     */
    POTATOES(3),
    /**
     * Null Crop
     */
    NULL_CROP( 0);

    private final int maxAge;
}
