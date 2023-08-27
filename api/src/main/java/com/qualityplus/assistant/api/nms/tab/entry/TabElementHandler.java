package com.qualityplus.assistant.api.nms.tab.entry;

import org.bukkit.entity.Player;

/**
 * Tab Element Handler
 */
public interface TabElementHandler {

    /**
     * Get the tab element of a player
     *
     * @param player the player
     * @return the element
     */
    public TabElement getElement(final Player player);

}
