package com.qualityplus.assistant.util.player;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class PlayerUtils {
    public boolean isPlacedByPlayer(final Block block) {
        return block.hasMetadata("theAssistantPlayerBlock");
    }

    public static String getPlayerName(final UUID uuid) {
        if (uuid == null) {
            return "";
        }
        return Optional.ofNullable(Bukkit.getOfflinePlayer(uuid))
                .map(OfflinePlayer::getName)
                .orElse("");
    }

    public List<String> parseWithName(final List<String> list, final Player player) {
        return list.stream()
                .map(line -> line.replace("%player%", player.getName()))
                .collect(Collectors.toList());
    }

    public List<Player> all() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }
}
