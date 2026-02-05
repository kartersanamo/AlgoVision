package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.commands.SubCommand;
import com.kartersanamo.algoVision.commands.AlgoCommand.AlgoCommand;
import org.bukkit.command.CommandSender;

/**
 * Displays help for all available /algo subcommands.
 */
public class HelpCommand extends BaseSubCommand {

    public HelpCommand(AlgoVision plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to view help.");
            return true;
        }

        sender.sendMessage("§6§lAlgoVision Help");
        sender.sendMessage("§eBase command: §f/algo <subcommand>");
        sender.sendMessage("§eAvailable subcommands:");

        org.bukkit.command.PluginCommand pc = plugin.getCommand("algo");
        if (pc != null && pc.getExecutor() instanceof AlgoCommand algo) {
            for (SubCommand sub : algo.getSubCommands()) {
                String name = sub.getName();
                if (name == null || name.isEmpty()) continue;
                String perm = sub.getPermission();
                if (perm != null && !perm.isEmpty() && !sender.hasPermission(perm)) continue;
                sender.sendMessage(" §6/algo " + name + " §7- §f" + sub.getDescription());
            }
        } else {
            // Fallback static help
            sender.sendMessage(" §6/algo wand §7- §fGet the region selection wand");
            sender.sendMessage(" §6/algo region §7- §fManage visualization regions");
        }

        return true;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show AlgoVision command help.";
    }

    @Override
    public String getUsage() {
        return "/algo help";
    }

    @Override
    public String getPermission() {
        return "algovision.help";
    }
}
