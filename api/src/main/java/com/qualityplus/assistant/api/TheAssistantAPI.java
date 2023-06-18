package com.qualityplus.assistant.api;

import com.qualityplus.assistant.api.commands.CommandProvider;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.api.nms.NMS;
import com.qualityplus.assistant.api.service.AddonsService;
import org.bukkit.plugin.Plugin;

/**
 * The assistant api
 */
public interface TheAssistantAPI {
    /**
     *
     * @return CommandProvider of {@link AssistantCommand}
     */
    public CommandProvider<AssistantCommand> getCommandProvider();

    /**
     *
     * @return {@link DependencyResolver}
     */
    public DependencyResolver getDependencyResolver();

    /**
     *
     * @return {@link AddonsService}
     */
    public AddonsService getAddons();

    /**
     *
     * @return {@link Plugin} instance
     */
    public Plugin getPlugin();

    /**
     *
     * @return {@link NMS}
     */
    public NMS getNms();
}
