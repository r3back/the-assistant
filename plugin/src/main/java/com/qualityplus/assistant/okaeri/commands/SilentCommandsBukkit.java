package com.qualityplus.assistant.okaeri.commands;

import eu.okaeri.commands.bukkit.CommandsBukkit;
import eu.okaeri.commands.bukkit.listener.AsyncTabCompleteListener;
import eu.okaeri.commands.bukkit.listener.PlayerCommandSendListener;
import lombok.NonNull;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Modified Okaeri CommandsBukkit with removed logs
 */
public final class SilentCommandsBukkit extends CommandsBukkit {
    private final JavaPlugin plugin;

    /**
     * Default Constructor
     *
     * @param plugin {@link JavaPlugin}
     */
    protected SilentCommandsBukkit(final @NonNull JavaPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    /**
     *
     * @param plugin {@link JavaPlugin}
     * @return {@link SilentCommandsBukkit}
     */
    public static SilentCommandsBukkit make(final JavaPlugin plugin) {
        if (plugin == null) {
            throw new NullPointerException("plugin is marked non-null but is null");
        } else {
            final SilentCommandsBukkit commandsBukkit = new SilentCommandsBukkit(plugin);
            commandsBukkit.registerListeners();
            return commandsBukkit;
        }
    }

    /**
     * Register command listeners
     */
    @Override
    public void registerListeners() {
        Class asyncTabCompleteEvent;
        try {
            asyncTabCompleteEvent = Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
            final PlayerCommandSendListener playerCommandSendListener = new PlayerCommandSendListener(this, asyncTabCompleteEvent);
            this.plugin.getServer().getPluginManager().registerEvent(asyncTabCompleteEvent, new Listener() {
            }, EventPriority.HIGHEST, playerCommandSendListener, this.plugin, true);
        } catch (ClassNotFoundException ignored) {
        }

        try {
            asyncTabCompleteEvent = Class.forName("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent");
            final AsyncTabCompleteListener asyncTabCompleteListener = new AsyncTabCompleteListener(this, asyncTabCompleteEvent);
            this.plugin.getServer().getPluginManager().registerEvent(asyncTabCompleteEvent, new Listener() {
            }, EventPriority.HIGHEST, asyncTabCompleteListener, this.plugin, true);
        } catch (ClassNotFoundException ignored) {
        }

    }
}
