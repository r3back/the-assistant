package com.qualityplus.assistant.base;

import com.qualityplus.assistant.api.TheAssistantAPI;
import com.qualityplus.assistant.api.commands.CommandProvider;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.api.dependency.resolver.DependencyResolver;
import com.qualityplus.assistant.api.nms.NMS;
import com.qualityplus.assistant.api.nms.tab.TabHandler;
import com.qualityplus.assistant.api.service.AddonsService;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

/**
 * The Assistant API Implementation
 */
@Getter
@Component
public final class TheAssistantAPIImpl implements TheAssistantAPI {
    private @Inject CommandProvider<AssistantCommand> commandProvider;
    private @Inject DependencyResolver dependencyResolver;
    private @Inject TabHandler tabHandler;
    private @Inject AddonsService addons;
    private @Inject Plugin plugin;
    private @Inject NMS nms;
}
