package com.qualityplus.assistant.api.common.rewards.commands;

import com.qualityplus.assistant.api.common.rewards.Reward;
import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Executable command reward implementation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CommandReward extends OkaeriConfig implements Reward {
    private CommandExecutor commandExecutor;
    private String command;

    @Override
    public void execute(final Player player) {
        final String cmd = this.command.replace("%player%", player.getName());

        if (this.commandExecutor.equals(CommandExecutor.CONSOLE)) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
        } else {
            player.performCommand(cmd);
        }
    }

    /**
     * Represent command executors
     */
    public enum CommandExecutor {
        /**
         * Console executor
         */
        CONSOLE,
        /**
         * Player Executor
         */
        PLAYER;
    }
}
