package com.kartersanamo.algoVision.commands.AlgoCommand;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands.*;
import com.kartersanamo.algoVision.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class AlgoCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommands;

    public AlgoCommand(AlgoVision plugin) {
        this.subCommands = new HashMap<>();

        // Register all subcommands
        registerSubCommand(new HelpCommand(plugin));
        registerSubCommand(new WandCommand(plugin));
        registerSubCommand(new RegionCommand(plugin));
        registerSubCommand(new SettingsCommand(plugin));
        registerSubCommand(new VisualizeCommand(plugin));
        registerSubCommand(new ExplainCommand(plugin));
        registerSubCommand(new ControlCommand(plugin));
        registerSubCommand(new PresetCommand(plugin));
        registerSubCommand(new DataCommand(plugin));
        registerSubCommand(new StatsCommand(plugin));
    }

    private void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);

        // Also register aliases
        for (String alias : subCommand.getAliases()) {
            subCommands.put(alias.toLowerCase(), subCommand);
        }
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        // No arguments - show help
        if (args.length == 0) {
            SubCommand helpCommand = subCommands.get("help");
            if (helpCommand != null) {
                return helpCommand.execute(sender, new String[0]);
            }
            sender.sendMessage("§cUsage: /algo <subcommand>");
            return false;
        }

        // Get subcommand
        String subCommandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            sender.sendMessage("§cUnknown subcommand: " + subCommandName);
            sender.sendMessage("§eUse §6/algo help §efor a list of commands");
            return false;
        }

        // Execute subcommand with remaining args
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.execute(sender, subArgs);
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        // First argument - suggest subcommand names
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            String partial = args[0].toLowerCase();

            for (String subCommandName : subCommands.keySet()) {
                SubCommand subCommand = subCommands.get(subCommandName);
                // Check permission
                if (subCommand.getPermission() == null ||
                        subCommand.getPermission().isEmpty() ||
                        sender.hasPermission(subCommand.getPermission())) {

                    if (subCommandName.startsWith(partial)) {
                        suggestions.add(subCommandName);
                    }
                }
            }

            Collections.sort(suggestions);
            return suggestions;
        }

        // Delegate to subcommand's tab completer
        if (args.length > 1) {
            String subCommandName = args[0].toLowerCase();
            SubCommand subCommand = subCommands.get(subCommandName);

            if (subCommand != null) {
                String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                return subCommand.onTabComplete(sender, subArgs);
            }
        }

        return List.of();
    }

    public Collection<SubCommand> getSubCommands() {
        // Return unique subcommands (excluding aliases)
        return new HashSet<>(subCommands.values());
    }
}