package com.qualityplus.assistant.api.commands;

import com.qualityplus.assistant.api.commands.setup.CommandHandler;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.List;

/**
 * Command Provider
 *
 * @param <T> Generic Command
 */
public interface CommandProvider<T> extends TabExecutor, TabCompleter {
    /**
     * Reload commands
     */
    public void reloadCommands();

    /**
     * Register commands
     */
    public void registerCommands();

    /**
     * Register a command
     *
     * @param command Generic Command to be registered
     * @param handler Command handler
     */
    public void registerCommand(final T command, final CommandHandler<T> handler);

    /**
     * Unregister specific command
     *
     * @param command Generic command
     */
    public void unregisterCommand(final T command);

    /**
     * Retrieves all commands registered
     *
     * @return List of Generic commands
     */
    public List<T> getCommands();
}
