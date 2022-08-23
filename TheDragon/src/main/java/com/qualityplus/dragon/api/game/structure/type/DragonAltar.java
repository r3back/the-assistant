package com.qualityplus.dragon.api.game.structure.type;

import com.qualityplus.dragon.api.game.structure.GameStructure;

public interface DragonAltar extends GameStructure {
    boolean isInUse();
    void setInUse(boolean inUse);
    void clearEye();
}
