package com.qualityplus.assistant.inventory;

import com.qualityplus.assistant.inventory.background.Background;

public interface SimpleGUI {
    public CommonGUI getCommonGUI();

    public default int getSize() {
        return getCommonGUI().size;
    }

    public default String getTitle() {
        return getCommonGUI().title;
    }

    public default Background getBackground() {
        return getCommonGUI().background;
    }

    public default Item getCloseGUI() {
        return getCommonGUI().closeGUI;
    }
}
