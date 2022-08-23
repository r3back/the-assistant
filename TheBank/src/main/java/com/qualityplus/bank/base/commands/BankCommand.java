package com.qualityplus.bank.base.commands;

import com.qualityplus.bank.api.box.Box;
import com.qualityplus.bank.base.gui.main.BankInterfaceGUI;
import com.qualityplus.assistant.TheAssistantPlugin;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.util.StringUtils;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

@Component
public final class BankCommand extends AssistantCommand {
    private @Inject Box box;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 1)
            player.openInventory(new BankInterfaceGUI(box, player, BankInterfaceGUI.GUIType.GENERAL).getInventory());
        else
            player.sendMessage(StringUtils.color(box.files().messages().pluginMessages.useSyntax.replace("%usage%", syntax)));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return args.length == 2 ? null : Collections.emptyList();
    }

    @Delayed(time = MinecraftTimeEquivalent.SECOND)
    public void register(@Inject Box box){
        TheAssistantPlugin.getAPI().getCommandProvider().registerCommand(this, e -> e.getCommand().setDetails(box.files().commands().bankCommand));
    }
}