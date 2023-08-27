package com.qualityplus.assistant.api.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bukkit.ChatColor;

/**
 * Team Info to handle Tab
 */
@Data
@Builder
@AllArgsConstructor
public final class TeamInfo {
    private final String name;
    private final ChatColor chatColor;
}
