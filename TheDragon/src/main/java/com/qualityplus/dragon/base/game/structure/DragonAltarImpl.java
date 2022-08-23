package com.qualityplus.dragon.base.game.structure;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.dragon.api.game.structure.type.DragonAltar;
import com.qualityplus.dragon.util.BlockUtils;
import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

@AllArgsConstructor
public final class DragonAltarImpl extends OkaeriConfig implements DragonAltar {
    private @Getter final Location location;
    private @Getter boolean isInUse;


    @Override
    public void removeStructure() {
        getLocation().getBlock().setType(Material.AIR);
    }

    @Override
    public World getWorld() {
        return location.getWorld();
    }

    @Override
    public void setInUse(boolean isInUse) {
        this.isInUse = isInUse;
        if(XMaterial.getVersion() > 13)
            BlockUtils.setBlockData(location, isInUse);
    }

    @Override
    public void clearEye() {
        if(XMaterial.getVersion() <= 13) return;

        if(location.getBlock() != null && location.getBlock().getType() != Material.AIR){
            isInUse = false;
            BlockUtils.setBlockData(location.getBlock(), false);
        }
    }
}
