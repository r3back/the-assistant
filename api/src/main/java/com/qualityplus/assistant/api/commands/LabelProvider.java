package com.qualityplus.assistant.api.commands;

import com.qualityplus.assistant.api.commands.handler.CommandLabelRegistry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

/**
 * Label Provider
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public final class LabelProvider {
    private final String id;
    private final String label;
    private final Plugin plugin;
    private final String useHelpMessage;
    private final String noPermissionMessage;
    private final String onlyForPlayersMessage;
    private final String unknownCommandMessage;

    /**
     * Register label provider
     */
    public void register() {
        CommandLabelRegistry.registerNewLabel(this);
    }
}
