package com.qualityplus.assistant.api.nms.tab.entry;

import com.qualityplus.assistant.api.nms.tab.skin.SkinType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Line in Tab
 */
@Getter
@Setter
public class TabElement {

    private final List<TabEntry> entries = new ArrayList<>();

    private String header = "";
    private String footer = "";

    /**
     * Get an entry by location in the tab element
     *
     * @param x the x axis
     * @param y the y axis
     * @return the entry
     */
    public TabEntry getEntry(final int x, final int y) {
        return this.entries.stream()
                .filter(entry -> entry.getX() == x && entry.getY() == y)
                .findFirst().orElseGet(() -> new TabEntry(x, y, "", 0));
    }

    /**
     * Add a new entry to the element
     *
     * @param entry the entry to add
     */
    public void add(final TabEntry entry) {
        this.entries.add(entry);
    }

    /**
     * Add a new entry to the element
     *
     * @param index the index to get the axiss from
     * @param text  the text to display on the slot
     */
    public void add(final int index, final String text) {
        this.add(index, text, -1);
    }

    /**
     * Add a new entry to the element
     *
     * @param x    the x axis
     * @param y    the y axis
     * @param text the text to display on the slot
     */
    public void add(final int x, final int y, final String text) {
        this.add(x, y, text, -1);
    }

    /**
     * Add a new entry to the element
     *
     * @param index the index to get the axiss from
     * @param text  the text to display on the slot
     * @param ping  the ping to display
     */
    public void add(final int index, final String text, final int ping) {
        this.add(index % 4, index / 4, text, ping);
    }

    /**
     * Add a new entry to the element
     *
     * @param x    the x axis
     * @param y    the y axis
     * @param text the text to display on the slot
     * @param ping the ping to display
     */
    public void add(final int x, final int y, final String text, final int ping) {
        this.entries.add(new TabEntry(x, y, text, ping));
    }

    /**
     * Add a new entry to the element
     *
     * @param index the index to get the axiss from
     * @param text     the text to display on the slot
     * @param ping     the ping to display
     * @param skinData the data to display in the skin slot
     */
    public void add(final int index, final String text, final int ping, final String[] skinData) {
        this.add(index % 4, index / 4, text, ping, skinData);
    }

    /**
     * Add a new entry to the element
     *
     * @param x        the x axis
     * @param y        the y axis
     * @param text     the text to display on the slot
     * @param ping     the ping to display
     * @param skinData the data to display in the skin slot
     */
    public void add(final int x, final int y, final String text, final int ping, final String[] skinData) {
        this.entries.add(new TabEntry(x, y, text, ping, skinData));
    }

    /**
     * Add a new entry to the element
     *
     * @param index the index to get the axiss from
     * @param text     the text to display on the slot
     * @param ping     the ping to display
     * @param skinType the data to display in the skin slot
     */
    public void add(final int index, final String text, final int ping, final SkinType skinType) {
        this.add(index, text, ping, skinType.getSkinData());
    }

    /**
     * Add a new entry to the element
     *
     * @param x        the x axis
     * @param y        the y axis
     * @param text     the text to display on the slot
     * @param ping     the ping to display
     * @param skinType the data to display in the skin slot
     */
    public void add(final int x, final int y, final String text, final int ping, final SkinType skinType) {
        this.add(x, y, text, ping, skinType.getSkinData());
    }
}
