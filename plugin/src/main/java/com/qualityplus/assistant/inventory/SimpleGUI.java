package com.qualityplus.assistant.inventory;

import com.qualityplus.assistant.inventory.background.Background;

/**
 * Simple GUI
 */
public interface SimpleGUI {
    /**
     * Retrieves Commons gui
     *
     * @return {@link CommonGUI}
     */
    public CommonGUI getCommonGUI();

    /**
     * Retrieves gui size
     *
     * @return gui size
     */
    public default int getSize() {
        return getCommonGUI().getSize();
    }

    /**
     * Retrieves gui title
     *
     * @return gui title
     */
    public default String getTitle() {
        return getCommonGUI().getTitle();
    }

    /**
     * Retrieves gui background
     *
     * @return gui background
     */
    public default Background getBackground() {
        return getCommonGUI().getBackground();
    }

    /**
     * Retrieves close gui item
     *
     * @return close gui item
     */
    public default Item getCloseGUI() {
        return getCommonGUI().getCloseGUI();
    }
}
