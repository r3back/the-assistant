package com.qualityplus.assistant.api;

import com.qualityplus.assistant.api.commands.CommandProvider;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.api.nms.NMS;
import com.qualityplus.assistant.api.service.AddonsService;
import org.bukkit.plugin.Plugin;

/**
 * The Assistant API
 */
public interface TheAssistantAPI {
    /**
     * Retrieves Command Provider
     *
     * @return CommandProvider of {@link AssistantCommand}
     */
    public CommandProvider<AssistantCommand> getCommandProvider();

    /**
     * Retrieves Dependency Resolver
     * @return {@link DependencyResolver}
     */
    public DependencyResolver getDependencyResolver();

    /**
     * Retrieves Addon Service
     *
     * @return {@link AddonsService}
     */
    public AddonsService getAddons();

    /**
     * Retrieves NMS Multi Version handler
     *
     * @return {@link NMS}
     */
    public NMS getNms();


    /**
     * Retrieves Plugin instance
     *
     * @return {@link Plugin} instance
     */
    public Plugin getPlugin();
}
