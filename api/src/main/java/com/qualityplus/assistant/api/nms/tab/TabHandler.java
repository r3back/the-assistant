package com.qualityplus.assistant.api.nms.tab;

import com.qualityplus.assistant.api.nms.tab.entry.TabElement;
import com.qualityplus.assistant.api.nms.tab.entry.TabElementHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Tab Handler
 */
@Getter
public class TabHandler {
    @Setter
    private TabElementHandler handler;

    private final TabAdapter adapter;

    private final long ticks;

    /**
     * Constructor to make a new tab handler.
     *
     * @param adapter the adapter to send the tab with
     * @param plugin  the plugin to register the thread to
     * @param ticks   the amount it should update
     * @deprecated as of Tab API 1.1-SNAPSHOT, replaced by
     *             {@link TabHandler#TabHandler(TabElementHandler, JavaPlugin, long)}
     */
    public TabHandler(final TabAdapter adapter, final JavaPlugin plugin, final long ticks) {
        this.adapter = adapter;
        this.ticks = ticks;

        new TabRunnable(this).runTaskTimerAsynchronously(plugin, 20L, ticks);
    }

    /**
     * Constructor to make a new tab handler
     *
     * @param handler the handler to get the elements from
     * @param plugin  the plugin to register the thread to
     * @param ticks   the amount it should update
     *
     * @throws ClassNotFoundException When class is not found
     * @throws IllegalAccessException when illegal access
     * @throws InstantiationException when instantiation fails
     */
    public TabHandler(
            final TabElementHandler handler,
            final JavaPlugin plugin,
            final long ticks
    ) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.adapter = this.createAdapter();
        this.handler = handler;
        this.ticks = ticks;

        new TabRunnable(this).runTaskTimerAsynchronously(plugin, 20L, ticks);
    }

    /**
     * Create a new adapter for the disguise handling
     *
     * @return the newly created adapter
     * @throws ClassNotFoundException thrown if the disguise class does not exist or could not be found
     * @throws IllegalAccessException thrown if the instantiation was invoked from an illegal instance
     * @throws InstantiationException thrown if something went wrong during class instantiation
     */
    private TabAdapter createAdapter() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        final String serverPackage = Bukkit.getServer().getClass().getPackage().getName();
        final String nmsVersion = serverPackage.replace(".", ",").split(",")[3].substring(1);
        final String disguisePackage = "com.qualityplus.assistant.base.nms.v" + nmsVersion;

        return (TabAdapter) Class.forName(disguisePackage + "_Tab").newInstance();
    }

    /**
     * Update the tablist for a player
     *
     * @param player the player to update it for
     */
    public void sendUpdate(final Player player) {
        if (this.handler == null) {
            return;
        }

        final TabElement tabElement = this.handler.getElement(player);

        this.adapter.setupProfiles(player)
                .showRealPlayers(player).addFakePlayers(player)
                .hideRealPlayers(player).handleElement(player, tabElement)
                .sendHeaderFooter(player, tabElement.getHeader(), tabElement.getFooter());
    }
}
