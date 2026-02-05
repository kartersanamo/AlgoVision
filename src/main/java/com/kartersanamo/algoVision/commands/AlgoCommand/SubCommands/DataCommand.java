package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DataCommand extends BaseSubCommand {

    private final DataManager dataManager;

    public DataCommand(AlgoVision plugin) {
        super(plugin);
        this.dataManager = plugin.getDataManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sendError(sender, "Only players can manage data.");
            return true;
        }
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to manage data.");
            return true;
        }

        if (args.length == 0) {
            sendInfo(sender, "Usage: " + getUsage());
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "generate":
                return handleGenerate(player, args);
            case "clear":
                dataManager.clearData(player.getUniqueId());
                sendSuccess(sender, "Your data has been cleared.");
                return true;
            case "info":
                int[] data = dataManager.getData(player.getUniqueId());
                if (data == null) {
                    sendInfo(sender, "You have no generated data.");
                } else {
                    sendInfo(sender, "Data size: " + data.length);
                }
                return true;
            default:
                sendError(sender, "Unknown subcommand: " + sub);
                sendInfo(sender, "Usage: " + getUsage());
                return true;
        }
    }

    private boolean handleGenerate(Player player, String[] args) {
        if (args.length < 2) {
            sendError(player, "Usage: /algo data generate <pattern> [size]");
            return false;
        }
        String pattern = args[1];
        int size = plugin.getConfigManager().getDataDefaultSize();
        if (args.length >= 3) {
            try {
                size = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {
            }
        }
        int[] data = dataManager.generateData(pattern, size);
        dataManager.setCustomData(player.getUniqueId(), data);
        sendSuccess(player, "Generated " + pattern + " data of size " + data.length + ".");
        return true;
    }

    @Override
    public String getName() {
        return "data";
    }

    @Override
    public String getDescription() {
        return "Generate or clear input data for visualizations.";
    }

    @Override
    public String getUsage() {
        return "/algo data <generate|clear|info> ...";
    }

    @Override
    public String getPermission() {
        return "algovision.data";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            for (String s : new String[]{"generate", "clear", "info"}) {
                if (s.startsWith(partial)) suggestions.add(s);
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("generate")) {
            String partial = args[1].toLowerCase();
            for (String p : new String[]{"random", "sorted", "reversed", "nearlysorted", "mountain", "valley"}) {
                if (p.startsWith(partial)) suggestions.add(p);
            }
        }
        return suggestions;
    }
}
