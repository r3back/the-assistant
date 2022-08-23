package com.qualityplus.dragon.base.configs.implementations;

import com.qualityplus.dragon.base.configs.*;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;

@Component
public final class ConfigFiles implements com.qualityplus.dragon.api.config.ConfigFiles<Config, DragonEventsFile, DragonGuardiansFile, DragonsFile, Inventories, Messages, DragonRewardsFile, StructuresFile, Commands> {
    private @Inject Config configuration;
    private @Inject DragonEventsFile dragonEvents;
    private @Inject DragonGuardiansFile dragonGuardians;
    private @Inject DragonsFile dragons;
    private @Inject Inventories inventories;
    private @Inject Messages messages;
    private @Inject Commands commands;
    private @Inject DragonRewardsFile rewards;
    private @Inject StructuresFile structures;

    @Override
    public void reloadFiles() {
        rewards.load();
        dragonGuardians.load();
        dragonEvents.load();
        configuration.load();
        messages.load();
        dragons.load();
        structures.load();
        commands.load();
    }


    @Override
    public Config config() {
        return configuration;
    }

    @Override
    public DragonEventsFile events() {
        return dragonEvents;
    }

    @Override
    public DragonGuardiansFile guardians() {
        return dragonGuardians;
    }

    @Override
    public DragonsFile dragons() {
        return dragons;
    }

    @Override
    public Inventories inventories() {
        return inventories;
    }

    @Override
    public Messages messages() {
        return messages;
    }

    @Override
    public DragonRewardsFile rewards() {
        return rewards;
    }

    @Override
    public StructuresFile structures() {
        return structures;
    }

    @Override
    public Commands commands() {
        return commands;
    }

    @Override
    public void saveFiles() {
        structures.save();
    }
}
