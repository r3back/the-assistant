package com.qualityplus.assistant.api.addons;

import com.qualityplus.assistant.api.dependency.DependencyPlugin;
import org.bukkit.entity.Entity;

/**
 * NPC Addon
 */
public interface NPCAddon extends DependencyPlugin {
    /**
     * Retrieves if an entity is an npc
     *
     * @param entity {@link Entity}
     * @return true if it"s npc
     */
    public boolean isNPC(final Entity entity);
}
