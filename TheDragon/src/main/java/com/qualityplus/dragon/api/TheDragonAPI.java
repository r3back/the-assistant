package com.qualityplus.dragon.api;

import com.qualityplus.dragon.api.service.*;
import org.bukkit.plugin.Plugin;

public interface TheDragonAPI {
    StructureService getStructureService();
    GuardianService getGuardianService();
    DragonService getDragonService();
    SetupService getSetupService();
    GameService getGameService();
    UserService getUserService();
    Plugin getPlugin();
}
