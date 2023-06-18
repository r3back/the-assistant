package com.qualityplus.assistant.api.addons.replacer;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

/**
 * Placeholder replace event
 */
@Getter
@Setter
public final class PlaceholderReplaceEvent {
    private OfflinePlayer player = null;

    /**
     * Default player constructor
     *
     * @param offlinePlayer {@link OfflinePlayer}
     */
    public PlaceholderReplaceEvent(final OfflinePlayer offlinePlayer) {
        this.player = offlinePlayer;
    }
}
