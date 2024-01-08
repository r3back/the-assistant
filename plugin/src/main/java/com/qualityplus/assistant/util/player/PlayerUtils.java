package com.qualityplus.assistant.util.player;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for players
 */
@UtilityClass
public class PlayerUtils {
    private static final String EMPTY_STRING = "";

    /**
     * Retrieves player name given an uuid
     *
     * @param uuid {@link UUID} player uuid
     * @return player"s name
     */
    public String getPlayerName(final UUID uuid) {
        if (uuid == null) {
            return EMPTY_STRING;
        }
        return Optional.ofNullable(Bukkit.getOfflinePlayer(uuid))
                .map(OfflinePlayer::getName)
                .orElse(EMPTY_STRING);
    }

    /**
     * Replace player name in list of string
     *
     * @param list   List of lines
     * @param player {@link Player}
     * @return List of lines with player"s name parsed
     */
    public List<String> parseWithName(final List<String> list, final Player player) {
        return list.stream()
                .map(line -> line.replace("%player%", player.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves list with all online players
     *
     * @return List of {@link Player}
     */
    public List<Player> all() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }
}
