package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.StatsManager;
import com.kartersanamo.algoVision.models.AlgorithmStats;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand extends BaseSubCommand {

    private final StatsManager statsManager;

    public StatsCommand(AlgoVision plugin) {
        super(plugin);
        this.statsManager = plugin.getStatsManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to view stats.");
            return true;
        }
        if (args.length == 0) {
            sendInfo(sender, "Usage: " + getUsage());
            return true;
        }
        String sub = args[0].toLowerCase();
        switch (sub) {
            case "recent":
                return handleRecent(sender, args);
            case "top":
                return handleTop(sender, args);
            default:
                sendError(sender, "Unknown subcommand: " + sub);
                sendInfo(sender, "Usage: " + getUsage());
                return true;
        }
    }

    private boolean handleRecent(CommandSender sender, String[] args) {
        int limit = 10;
        if (args.length >= 2) {
            try {
                limit = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {
            }
        }
        List<AlgorithmStats> list = statsManager.getHistory(limit);
        if (list.isEmpty()) {
            sendInfo(sender, "No stats recorded yet.");
            return true;
        }
        sender.sendMessage("§6Recent runs (" + list.size() + "):");
        int i = 1;
        for (AlgorithmStats s : list) {
            sender.sendMessage(" §e#" + (i++) + "§7 - §fcomparisons=" + s.getComparisons()
                    + ", swaps=" + s.getSwaps() + ", time=" + s.getDurationMs() + "ms");
        }
        return true;
    }

    private boolean handleTop(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sendError(sender, "Usage: /algo stats top <algorithm> [limit]");
            return false;
        }
        String algorithm = args[1];
        int limit = 10;
        if (args.length >= 3) {
            try {
                limit = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {
            }
        }
        List<AlgorithmStats> list = statsManager.getLeaderboard(algorithm, limit);
        if (list.isEmpty()) {
            sendInfo(sender, "No stats for algorithm " + algorithm + ".");
            return true;
        }
        sender.sendMessage("§6Top runs for " + algorithm + ":");
        int i = 1;
        for (AlgorithmStats s : list) {
            sender.sendMessage(" §e#" + (i++) + "§7 - §ftime=" + s.getDurationMs() + "ms, comparisons=" + s.getComparisons());
        }
        return true;
    }
    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "View algorithm performance statistics.";
    }

    @Override
    public String getUsage() {
        return "/algo stats <recent|top> ...";
    }

    @Override
    public String getPermission() {
        return "algovision.stats";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            for (String s : new String[]{"recent", "top"}) {
                if (s.startsWith(partial)) suggestions.add(s);
            }
        }
        return suggestions;
    }
}
