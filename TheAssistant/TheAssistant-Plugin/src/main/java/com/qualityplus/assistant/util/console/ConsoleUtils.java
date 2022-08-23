package com.qualityplus.assistant.util.console;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.List;

@UtilityClass
public final class ConsoleUtils {
    public static void msg(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static void cmd(String command){
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void cmd(List<String> commands){
        commands.forEach(ConsoleUtils::cmd);
    }
}
