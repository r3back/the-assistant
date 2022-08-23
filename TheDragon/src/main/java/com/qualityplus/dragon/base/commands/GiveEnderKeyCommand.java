package com.qualityplus.dragon.base.commands;

import com.qualityplus.assistant.TheAssistantPlugin;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.itemstack.ItemStackUtils;
import com.qualityplus.assistant.util.math.MathUtils;
import com.qualityplus.dragon.api.box.Box;
import com.qualityplus.dragon.util.DragonItemStackUtil;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

@Component
public final class GiveEnderKeyCommand extends AssistantCommand {
    private @Inject Box box;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length == 3){
            Player player = Bukkit.getPlayer(args[1]);

            if(player != null) {
                Integer amount = MathUtils.intOrNull(args[2]);
                if(amount != null){
                    ItemStack itemStack = DragonItemStackUtil.createEnderKey(ItemStackUtils.makeItem(box.files().config().enderKeyItem));

                    for(int i = 0; i<amount; i++) player.getInventory().addItem(itemStack);
                }else{
                    sender.sendMessage(StringUtils.color(box.files().messages().pluginMessages.invalidAmount.replace("%prefix%", box.files().config().prefix)));
                }
            }else{
                sender.sendMessage(StringUtils.color(box.files().messages().pluginMessages.invalidPlayer.replace("%prefix%", box.files().config().prefix)));
            }
        }else{
            sender.sendMessage(StringUtils.color(box.files().messages().pluginMessages.useSyntax.replace("%usage%", syntax)));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Delayed(time = MinecraftTimeEquivalent.SECOND)
    public void register(@Inject Box box){
        TheAssistantPlugin.getAPI().getCommandProvider().registerCommand(this, e -> e.getCommand().setDetails(box.files().commands().giveEnderKeyCommand));
    }
}
