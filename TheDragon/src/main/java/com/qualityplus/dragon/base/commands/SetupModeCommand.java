package com.qualityplus.dragon.base.commands;

import com.qualityplus.assistant.TheAssistantPlugin;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.api.service.SetupService;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public final class SetupModeCommand extends AssistantCommand {
    private @Inject SetupService setupService;
    private @Inject Box box;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length == 1){
            UUID uuid = player.getUniqueId();
            Set<UUID> editorMode = setupService.getPlayersInSetupMode();

            if(editorMode.contains(uuid)){
                editorMode.remove(uuid);
                player.sendMessage(StringUtils.color(box.files().messages().setupMessages.setupModeLeft.replace("%prefix%", box.files().config().prefix)));
            }else{
                editorMode.add(uuid);
                box.files().messages().setupMessages.setupModeJoin.forEach(message -> player.sendMessage(StringUtils.color(message.replace("%prefix%", box.files().config().prefix))));
            }
        }else{
            player.sendMessage(StringUtils.color(box.files().messages().pluginMessages.useSyntax.replace("%usage%", syntax)));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }


    @Delayed(time = MinecraftTimeEquivalent.SECOND)
    public void register(@Inject Box box){
        TheAssistantPlugin.getAPI().getCommandProvider().registerCommand(this, e -> e.getCommand().setDetails(box.files().commands().setupModeCommand));
    }

}
