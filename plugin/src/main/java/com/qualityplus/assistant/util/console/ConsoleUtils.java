package com.qualityplus.assistant.util.console;

import com.qualityplus.assistant.util.StringUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.List;

@UtilityClass
public final class ConsoleUtils {
    public static void msg(final String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static void colored(final String message) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.color(message));
    }

    public static void cmd(final String command) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void cmd(final List<String> commands) {
        commands.forEach(ConsoleUtils::cmd);
    }
}
