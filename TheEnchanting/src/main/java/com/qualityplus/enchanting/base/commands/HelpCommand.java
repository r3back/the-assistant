package com.qualityplus.enchanting.base.commands;

import com.qualityplus.assistant.TheAssistantPlugin;
import com.qualityplus.assistant.api.commands.CommandProvider;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.enchanting.api.box.Box;
import com.qualityplus.enchanting.base.config.Messages;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

@Component
public final class HelpCommand extends AssistantCommand {
    private @Inject Box box;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Messages.PluginMessages msg = box.files().messages().pluginMessages;
        sendHelpCommands(sender, args, TheAssistantPlugin.getAPI().getCommandProvider(), msg.helpHeader, msg.helpMessage, msg.helpfooter, msg.nextPage, msg.previousPage, msg.helpPageHoverMessage);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Delayed(time = MinecraftTimeEquivalent.SECOND)
    public void register(@Inject Box box){
        TheAssistantPlugin.getAPI().getCommandProvider().registerCommand(this, e -> e.getCommand().setDetails(box.files().commands().helpCommand));
    }
}