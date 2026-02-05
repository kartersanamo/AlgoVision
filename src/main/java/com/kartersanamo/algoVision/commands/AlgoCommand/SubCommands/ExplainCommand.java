package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.models.AlgorithmInfo;
import com.kartersanamo.algoVision.utils.AlgorithmRegistry;
import com.kartersanamo.algoVision.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Explains an algorithm without running it: description, how it works, time/space complexity.
 */
public class ExplainCommand extends BaseSubCommand {

    public ExplainCommand(AlgoVision plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            sendError(sender, "Only players can use this command.");
            return true;
        }
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            sendInfo(sender, "Usage: " + getUsage());
            sendInfo(sender, "Example: /algo explain sorting bubble");
            return true;
        }

        String category = args[0].toLowerCase();
        String algorithm = args[1].toLowerCase();
        Optional<AlgorithmInfo> opt = AlgorithmRegistry.get(category, algorithm);

        if (opt.isEmpty()) {
            sendError(sender, "Unknown algorithm: " + category + " / " + algorithm);
            sendInfo(sender, "Use /algo visualize (tab-complete) to see available categories and algorithms.");
            return true;
        }

        MessageUtils.sendAlgorithmExplain((Player) sender, opt.get());
        return true;
    }

    @Override
    public String getName() {
        return "explain";
    }

    @Override
    public String getDescription() {
        return "Explain an algorithm (what it does, how it works, complexity) without running it.";
    }

    @Override
    public String getUsage() {
        return "/algo explain <category> <algorithm>";
    }

    @Override
    public String getPermission() {
        return "algovision.help";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            for (String cat : new String[]{"sorting", "searching", "graph", "tree", "pathfinding", "dynamic"}) {
                if (cat.startsWith(partial)) suggestions.add(cat);
            }
        } else if (args.length == 2) {
            String category = args[0].toLowerCase();
            String partial = args[1].toLowerCase();
            String[] algos = switch (category) {
                case "sorting" -> new String[]{"bubble", "quick", "merge", "insertion", "selection", "heap", "shell"};
                case "searching" -> new String[]{"binary", "linear", "jump"};
                case "graph" -> new String[]{"bfs", "dfs", "dijkstra"};
                case "tree" -> new String[]{"bst"};
                case "pathfinding" -> new String[]{"astar"};
                case "dynamic" -> new String[]{"fibonacci"};
                default -> new String[0];
            };
            for (String a : algos) {
                if (a.startsWith(partial)) suggestions.add(a);
            }
        }
        return suggestions;
    }
}
