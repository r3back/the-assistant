package com.qualityplus.pets.base.pet;

import com.cryptomorin.xseries.XMaterial;
import com.qualityplus.assistant.api.common.rewards.commands.CommandRewards;
import com.qualityplus.pets.base.pet.gui.PetGUIOptions;
import eu.okaeri.configs.OkaeriConfig;
import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public final class Pet extends OkaeriConfig {
    private String id;
    private String displayName;
    private String entityName;
    private String description;
    private PetGUIOptions petGUIOptions;
    private boolean enabled;

    private int maxLevel;
    private CommandRewards commandRewards;
    private Map<Integer, Double> xpRequirements;
    private Map<Integer, List<String>> skillsInfoInGUI;
    private Map<Integer, List<String>> skillsInfoInMessage;

    private Map<XMaterial, Double> rewardsPerEach;
    private double rewardsForAll;
}
