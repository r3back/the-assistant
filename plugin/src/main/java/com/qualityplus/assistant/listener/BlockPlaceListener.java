package com.qualityplus.assistant.listener;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

@Component
public final class BlockPlaceListener implements Listener {
    private static final String METADATA_VALUE = "theAssistantPlayerBlock";
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(final BlockPlaceEvent e) {

        final FixedMetadataValue metadata = new FixedMetadataValue(plugin, METADATA_VALUE);

        e.getBlock().setMetadata(METADATA_VALUE, metadata);
    }
}
