package com.qualityplus.assistant.base.addons.npc;

import com.qualityplus.assistant.api.addons.NPCAddon;
import org.bukkit.entity.Entity;

/**
 * No NPC Plugin Implementation
 */
public final class DefaultNPCAddon implements NPCAddon {
    @Override
    public String getAddonName() {
        return null;
    }

    @Override
    public boolean isNPC(final Entity entity) {
        return false;
    }
}
