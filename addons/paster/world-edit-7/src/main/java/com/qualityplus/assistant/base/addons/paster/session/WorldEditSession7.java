package com.qualityplus.assistant.base.addons.paster.session;

import com.qualityplus.assistant.api.addons.paster.cuboid.Cuboid;
import com.qualityplus.assistant.api.addons.paster.session.PasterSession;
import com.sk89q.worldedit.EditSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * World edit 6 paster session implementation
 */
@Getter
@AllArgsConstructor
public final class WorldEditSession7 implements PasterSession {
    private final EditSession editSession;
    private final Cuboid cuboid;

    @Override
    public void undo() {
        Optional.ofNullable(this.editSession).ifPresent(session -> session.undo(session));
    }

    @Override
    public void setAllBlocks(Material material) {
        this.cuboid.getBlocks().forEach(block -> block.setType(material));
    }

    @Override
    public List<Block> getAllBlocks() {
        return new ArrayList<>(this.cuboid.getBlocks());
    }

    @Override
    public boolean isInside(Location location) {
        return this.cuboid.isInside(location);
    }
}
