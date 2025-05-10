package com.qualityplus.assistant.base.commands;

import com.qualityplus.assistant.api.commands.CommandProvider;
import com.qualityplus.assistant.api.commands.LabelProvider;
import com.qualityplus.assistant.api.commands.command.AssistantCommand;
import com.qualityplus.assistant.api.commands.handler.CommandLabelRegistry;
import com.qualityplus.assistant.api.commands.setup.CommandHandler;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.list.ListUtils;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Command provider implementation for the assistant
 */
@Component
public final class TheAssistantCommandProvider implements CommandProvider<AssistantCommand> {
    private final Map<String, List<AssistantCommand>> commands = new HashMap<>();
    private static final String REGISTER_ERROR_MSG = "Error Registering %s %s";
    private static final String PERMISSION_NODE = "thecore.";
    private static final String EMPTY_PERMISSION = "";
    private @Inject Logger logger;

    /**
     * Register a command with specific handler
     *
     * @param command Command to be registered
     * @param handler Command handler
     */
    @Override
    public void registerCommand(final AssistantCommand command, final CommandHandler<AssistantCommand> handler) {
        command.setup(handler);

        final Optional<LabelProvider> provider = getLabelProvider(command.getLabelProvider());

        if (!provider.isPresent()) {
            this.logger.warning(String.format(REGISTER_ERROR_MSG, command.getLabelProvider(), command.getAliases().get(0)));
            return;
        }

        registerBukkitCommand(provider.get(), command.getLabelProvider());

        final String label = provider.get().getLabel();

        this.commands.put(label, ListUtils.listWith(this.commands.getOrDefault(label, new ArrayList<>()), command));
    }

    /**
     * Reload all commands
     */
    @Override
    public void reloadCommands() {
        this.commands.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(AssistantCommand::reload);
    }

    /**
     * Register all commands
     */
    @Delayed(time = MinecraftTimeEquivalent.SECOND, async = true)
    public void registerCommands() {
        final Iterator<List<AssistantCommand>> iterator = this.commands.values().iterator();

        //TODO refactor whole command provider
        while (iterator.hasNext()) {
            final List<AssistantCommand> list = iterator.next();

            if (list == null) {
                break;
            }

            list.sort(Comparator.comparing(command -> command.getAliases().get(0)));
        }
    }

    @Override
    public void unregisterCommand(final AssistantCommand command) {
        getLabelProvider(command.getLabelProvider())
                .ifPresent(labelProvider -> this.commands.get(labelProvider.getLabel()).remove(command));
    }

    @Override
    public List<AssistantCommand> getCommands() {
        return this.commands.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender,
                             final @NotNull Command cmd,
                             final @NotNull String label,
                             final String[] args) {

        final Optional<LabelProvider> labelProvider = getLabelProvider(cmd.getName());

        if (args.length == 0) {
            final Optional<AssistantCommand> emptyArgsCmd = labelProvider.map(LabelProvider::getEmptyArgsCommand);
            if (emptyArgsCmd.isPresent()) {
                executeCommand(labelProvider, sender, args, emptyArgsCmd.get(), true);
            } else {
                sendMessage(sender, labelProvider, LabelProvider::getUseHelpMessage);
            }
            return true;
        }

        for (AssistantCommand command : getCommandsByLabel(cmd.getName())) {
            if (!executeCommand(labelProvider, sender, args, command, false)) {
                continue;
            }
            return true;
        }

        sendMessage(sender, labelProvider, LabelProvider::getUnknownCommandMessage);

        return false;
    }

    private boolean executeCommand(final Optional<LabelProvider> labelProvider,
                                   final @NotNull CommandSender sender,
                                   final String[] args,
                                   final AssistantCommand command,
                                   final boolean isNoArgsCommand) {
        if (!command.isEnabled()) {
            return false;
        }

        if (!isNoArgsCommand) {
            if (!command.getAliases().contains(args[0])) {
                return false;
            }
        } else {
            if (args.length != 0) {
                sender.sendMessage(StringUtils.color(labelProvider.map(LabelProvider::getNoArgsUsageMessage).orElse("&cNot found no args usage message.")));
                return false;
            }
        }


        if (command.isOnlyForPlayers() && !(sender instanceof Player)) {
            sendMessage(sender, labelProvider, LabelProvider::getOnlyForPlayersMessage);
            return true;
        }

        if (!(sender.hasPermission(command.getPermission()) || command.getPermission()
                .equalsIgnoreCase(EMPTY_PERMISSION) || command.getPermission()
                .equalsIgnoreCase(PERMISSION_NODE))) {

            sendMessage(sender, labelProvider, LabelProvider::getNoPermissionMessage);
            return true;
        }

        command.execute(sender, args);
        return true;
    }

    @SuppressWarnings("all")
    private void sendMessage(final CommandSender sender, final Optional<LabelProvider> provider,
                             final Function<LabelProvider, String> function) {
        sender.sendMessage(StringUtils.color(provider
                .map(LabelProvider::getUseHelpMessage)
                .map(msg -> msg.replace("%label%", provider.map(LabelProvider::getLabel).orElse("")))
                .orElse(EMPTY_PERMISSION)));
    }

    @Override
    public List<String> onTabComplete(final @NotNull CommandSender commandSender,
                                      final @NotNull Command cmd,
                                      final @NotNull String label,
                                      final String[] args) {

        if (args.length == 1) {
            final List<String> result = new ArrayList<>();
            for (final AssistantCommand command : getCommandsByLabel(cmd.getName())) {
                for (final String alias : command.getAliases()) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (
                            command.isEnabled() && (commandSender.hasPermission(command.getPermission())
                                    || command.getPermission().equalsIgnoreCase(EMPTY_PERMISSION)
                                    || command.getPermission().equalsIgnoreCase(PERMISSION_NODE)))) {
                        result.add(alias);
                    }
                }
            }
            return result;
        }

        for (AssistantCommand command : getCommandsByLabel(cmd.getName())) {
            if (command.getAliases().contains(args[0]) && (command.isEnabled() && (
                    commandSender.hasPermission(command.getPermission())
                            || command.getPermission().equalsIgnoreCase(EMPTY_PERMISSION)
                            || command.getPermission().equalsIgnoreCase(PERMISSION_NODE)))) {
                return command.onTabComplete(commandSender, cmd, label, args);
            }
        }

        return null;
    }

    private List<AssistantCommand> getCommandsByLabel(final String label) {
        return getLabelProvider(label)
                .map(LabelProvider::getLabel)
                .map(this.commands::get)
                .orElse(new ArrayList<>());
    }

    private Optional<LabelProvider> getLabelProvider(final String label) {
        return CommandLabelRegistry.values().stream()
                .filter(labelProvider -> labelProvider.getId().equals(label))
                .findFirst();
    }

    private void registerBukkitCommand(final LabelProvider provider, final String label) {
        final JavaPlugin java = (JavaPlugin) provider.getPlugin();

        final PluginCommand command = java.getCommand(label);

        if (command == null) {
            return;
        }

        if (!(command.getExecutor() instanceof TheAssistantCommandProvider)) {
            command.setExecutor(this);
        }

        if (command.getTabCompleter() == null || !(command.getTabCompleter() instanceof TheAssistantCommandProvider)) {
            command.setTabCompleter(this);
        }
    }
}
