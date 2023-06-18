package com.qualityplus.assistant.base.addons.npc;

import com.qualityplus.assistant.api.addons.NPCAddon;
import org.bukkit.entity.Entity;

/**
 * Citizens Implementation
 */
public final class CitizensAddon implements NPCAddon {
    @Override
    public String getAddonName() {
        return "Citizens";
    }

    @Override
    public boolean isNPC(final Entity entity) {
        return entity.hasMetadata("NPC");
    }
}
