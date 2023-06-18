package com.qualityplus.assistant.base.addons.mmoitems;

import com.qualityplus.assistant.api.addons.MMOItemsAddon;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;

import java.util.Optional;
import java.util.UUID;

/**
 * MMOItems implementation
 */
public final class MMOItemsAddonImpl implements MMOItemsAddon {
    @Override
    public String getAddonName() {
        return "MMOItems";
    }

    @Override
    public void updateStats(final UUID uuid, final String ability, final String type, final double value) {
        final StatMap stats = MMOPlayerData.get(uuid).getStatMap();

        final StatInstance attribute = stats.getInstance(ability);

        final String key = "HC_" + ability + type;

        final StatModifier modifier = attribute.getModifier(key);

        if (modifier == null || modifier.getValue() != value) {
            attribute.addModifier(new StatModifier(key, type, value));
        }
    }

    @Override
    public double getStats(final UUID uuid, final String ability) {
        return Optional.ofNullable(MMOPlayerData.get(uuid))
                .map(data -> data.getStatMap().getStat(ability))
                .orElse(0D);
    }

    @Override
    public double getMMOArmor(final UUID uuid, final String ability) {
        return Optional.ofNullable(MMOPlayerData.get(uuid))
                .map(data -> Optional.ofNullable(data.getStatMap()
                        .getInstance(ability)
                        .getModifier("MMOItem"))
                        .map(StatModifier::getValue)
                        .orElse(0D))
                .orElse(0D);
    }
}
