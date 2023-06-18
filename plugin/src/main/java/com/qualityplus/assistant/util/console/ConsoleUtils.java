package com.qualityplus.assistant.util.console;

import com.qualityplus.assistant.util.StringUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * Console Utility class
 */
@UtilityClass
public final class ConsoleUtils {
    /**
     * Sends a simple message
     *
     * @param message message to be sent
     */
    public static void msg(final String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    /**
     * Sends a colored message
     *
     * @param message message to be sent
     */
    public static void colored(final String message) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.color(message));
    }

    /**
     * Executes a console command
     *
     * @param command command to be executed
     */
    public static void cmd(final String command) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    /**
     * Execute a list of command trough console
     *
     * @param commands List of commands to be executed
     */
    public static void cmd(final List<String> commands) {
        commands.forEach(ConsoleUtils::cmd);
    }
}
