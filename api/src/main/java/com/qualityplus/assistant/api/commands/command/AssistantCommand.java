package com.qualityplus.assistant.api.commands.command;

import com.qualityplus.assistant.api.commands.CommandProvider;
import com.qualityplus.assistant.api.commands.details.CommandDetails;
import com.qualityplus.assistant.api.commands.setup.event.CommandSetupEvent;
import com.qualityplus.assistant.api.commands.setup.CommandHandler;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract command class
 */
public abstract class AssistantCommand extends CommandDetails {
    private CommandHandler<AssistantCommand> commandCompleteHandler;
    private CommandDetails commandDetails;

    /**
     * Retrieves if command was successfully executed
     *
     * @param sender {@link CommandSender}
     * @param args   command arguments
     * @return true if command was executed
     */
    public abstract boolean execute(CommandSender sender, String[] args);

    /**
     * Handle command tab completion
     *
     * @param commandSender {@link CommandSender}
     * @param command       {@link Command}
     * @param label         command label
     * @param args          command arguments
     * @return List of {@link String}
     */
    public abstract List<String> onTabComplete(final CommandSender commandSender, final Command command, final String label, final String[] args);

    /**
     * Set command details
     *
     * @param details {@link CommandDetails}
     */
    public void setDetails(final CommandDetails details) {
        this.commandDetails = details;
    }

    /**
     *
     * @param commandSetupHandler CommandSetupHandler of {@link AssistantCommand}
     */
    public void setup(final CommandHandler<AssistantCommand> commandSetupHandler) {
        this.commandCompleteHandler = commandSetupHandler;
        this.reload();
    }

    /**
     * Reload command info
     */
    public void reload() {
        //Setup Details
        this.commandCompleteHandler.onCompleteCommand(new CommandSetupEvent<>(this));
        super.cooldownInSeconds = this.commandDetails.getCooldownInSeconds();
        super.onlyForPlayers = this.commandDetails.isOnlyForPlayers();
        super.labelProvider = this.commandDetails.getLabelProvider();
        super.description = this.commandDetails.getDescription();
        super.permission = this.commandDetails.getPermission();
        super.enabled = this.commandDetails.isEnabled();
        super.aliases = this.commandDetails.getAliases();
        super.syntax = this.commandDetails.getSyntax();
    }


    /**
     *
     * @param args          Command arguments
     * @param sender        {@link CommandSender}
     * @param gui           {@link InventoryHolder}
     * @param useSyntax     Use syntax message
     * @param mustBeAPlayer Must be a player message
     * @param invalidPlayer Invalid player message
     * @return true if inventory was open
     */
    protected boolean openInventory(final String[] args, final CommandSender sender, final InventoryHolder gui, final String useSyntax,
                                    final String mustBeAPlayer, final String invalidPlayer) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage(useSyntax);
            return false;
        }

        if (args.length == 1 && !(sender instanceof Player)) {
            sender.sendMessage(mustBeAPlayer);
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 2) {
            player = Bukkit.getPlayer(args[1]);
        }

        if (player == null || !player.isOnline()) {
            sender.sendMessage(invalidPlayer);
            return false;
        }

        player.openInventory(gui.getInventory());

        return true;
    }


    /**
     *
     * @param sender               {@link CommandSender}
     * @param args                 Help arguments
     * @param commands             CommandProvider of {@link AssistantCommand}
     * @param helpHeader           Help header message
     * @param helpMessage          Help message
     * @param helpFooter           Help footer message
     * @param nextPage             Next page message
     * @param previousPage         Previous page message
     * @param helpPageHoverMessage Help Page Hover Message
     */
    protected void sendHelpCommands(final CommandSender sender, final String[] args, final CommandProvider<AssistantCommand> commands,
                                    final String helpHeader, final String helpMessage, final String helpFooter, final String nextPage,
                                    final String previousPage, final String helpPageHoverMessage) {
        final Player p = (Player) sender;
        int page = 1;
        if (args.length == 2) {
            if (!org.apache.commons.lang.StringUtils.isNumeric(args[1])) {
                return;
            }

            page = Integer.parseInt(args[1]);
        }

        final double maxPerPage = 8;
        final List<AssistantCommand> list = commands.getCommands()
                .stream()
                .filter(command -> command.labelProvider.equals(labelProvider))
                .collect(Collectors.toList());

        final int maxpage = (int) Math.ceil(list.size() / maxPerPage);
        int current = 0;
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', helpHeader));
        for (AssistantCommand command : list) {
            if ((p.hasPermission(command.permission)
                    || command.permission.equalsIgnoreCase("")
                    || command.permission.equalsIgnoreCase(command.labelProvider + ".")) && command.enabled) {
                if (current >= (page - 1) * maxPerPage && current < page * maxPerPage) {
                    p.sendMessage(getHelpMessage(helpMessage, command));
                }
                current++;
            }
        }
        final BaseComponent[] components = TextComponent.fromLegacyText(getFooterMessage(helpFooter, maxpage, page));

        for (BaseComponent component : components) {
            if (ChatColor.stripColor(component.toLegacyText()).contains(nextPage)) {
                if (page < maxpage) {
                    component.setClickEvent(getClickEvent(this.commandDetails.getLabelProvider(), page + 1));
                    component.setHoverEvent(getHoverEvent(helpPageHoverMessage, page + 1));
                }
            } else if (ChatColor.stripColor(component.toLegacyText()).contains(previousPage)) {
                if (page > 1) {
                    component.setClickEvent(getClickEvent(this.commandDetails.getLabelProvider(), page - 1));
                    component.setHoverEvent(getHoverEvent(helpPageHoverMessage, page - 1));
                }
            }
        }
        p.getPlayer().spigot().sendMessage(components);
    }

    private ClickEvent getClickEvent(final String labelProvider, final int page) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + labelProvider + " help " + page);
    }

    private HoverEvent getHoverEvent(final String helpPageHoverMessage, final int page) {
        final ComponentBuilder builder = new ComponentBuilder(helpPageHoverMessage.replace("%page%", "" + page));
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, builder.create());
    }

    private String getFooterMessage(final String helpFooter, final int maxPage, final int page) {
        final String text = helpFooter
                .replace("%maxpage%", maxPage + "")
                .replace("%page%", page + "");

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private String getHelpMessage(final String helpMessage, final AssistantCommand command) {
        final String text = helpMessage
                .replace("%command%", command.syntax)
                .replace("%description%", command.description);

        return ChatColor.translateAlternateColorCodes('&', text);

    }
}
