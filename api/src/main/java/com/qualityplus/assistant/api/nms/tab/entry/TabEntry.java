package com.qualityplus.assistant.api.nms.tab.entry;

import com.qualityplus.assistant.api.nms.tab.skin.SkinType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Entry in tab
 */
@Getter
@Setter
public class TabEntry {

    private final int x;
    private final int y;
    private final String text;
    private final int ping;

    private String[] skinData;

    /**
     * Constructor to make a new tab entry object with provided skin data
     *
     * @param x    the x axis
     * @param y    the y axis
     * @param text the text to display on the slot
     */
    public TabEntry(final int x, final int y, final String text) {
        this(x, y, text, 0, SkinType.DARK_GRAY.getSkinData());
    }

    /**
     * Constructor to make a new tab entry object with provided skin data
     *
     * @param x    the x axis
     * @param y    the y axis
     * @param text the text to display on the slot
     * @param ping     the displayed latency
     */
    public TabEntry(final int x, final int y, final String text, final int ping) {
        this(x, y, text, ping, SkinType.DARK_GRAY.getSkinData());
    }

    /**
     * Constructor to make a new tab entry object with provided skin data
     *
     * @param x        the x axis
     * @param y        the y axis
     * @param text     the text to display on the slot
     * @param ping     the displayed latency
     * @param skinData the data to display in the skin slot
     */
    public TabEntry(final int x, final int y, final String text, final int ping, final String[] skinData) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.ping = ping;
        this.skinData = skinData;
    }
}
