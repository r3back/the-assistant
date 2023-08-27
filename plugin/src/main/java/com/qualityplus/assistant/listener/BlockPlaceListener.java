package com.qualityplus.assistant.listener;

import com.qualityplus.assistant.util.block.BlockUtils;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * Block place listener
 */
@Component
public final class BlockPlaceListener implements Listener {
    private @Inject Plugin plugin;

    /**
     *
     * @param e {@link BlockPlaceEvent}
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(final BlockPlaceEvent e) {

        final FixedMetadataValue metadata = new FixedMetadataValue(this.plugin, BlockUtils.getBlockMetadataValue());

        e.getBlock().setMetadata(BlockUtils.getBlockMetadataValue(), metadata);
    }
}
