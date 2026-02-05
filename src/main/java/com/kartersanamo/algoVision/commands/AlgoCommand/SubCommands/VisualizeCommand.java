package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.RegionManager;
import com.kartersanamo.algoVision.models.Region;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.models.VisualizationSettings;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import com.kartersanamo.algoVision.visualizers.VisualizerFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VisualizeCommand extends BaseSubCommand {

    private final VisualizerFactory factory;
    private final RegionManager regionManager;

    public VisualizeCommand(AlgoVision plugin) {
        super(plugin);
        this.factory = new VisualizerFactory(plugin);
        this.regionManager = plugin.getRegionManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            sendError(sender, "Only players can start visualizations.");
            return true;
        }
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to start visualizations.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            sendInfo(sender, "Usage: " + getUsage());
            return true;
        }

        String category = args[0].toLowerCase();
        String algorithm = args[1].toLowerCase();

        Region region = regionManager.getRegion(player);
        if (region == null || !region.isComplete()) {
            sendError(sender, "You must select a complete region first (use /algo wand and /algo region).");
            return true;
        }
        if (!regionManager.validateRegion(region)) {
            sendError(sender, "Selected region is invalid or exceeds configured limits.");
            return true;
        }

        VisualizationSettings settings = plugin.getSettingsManager().getSettings(player);
        UUID sessionId = UUID.randomUUID();
        String algoName = category + ":" + algorithm;
        VisualizationSession session = new VisualizationSession(sessionId, player.getUniqueId(), algoName, region, settings);

        Visualizer visualizer;
        try {
            visualizer = factory.createVisualizer(category, algorithm, session);
        } catch (IllegalArgumentException ex) {
            sendError(sender, ex.getMessage());
            return true;
        }

        session.setVisualizer(visualizer);
        plugin.getVisualizationManager().registerSession(session);
        visualizer.start();

        sendSuccess(sender, "Started visualization " + algoName + " (session " + sessionId + ").");
        return true;
    }

    @Override
    public String getName() {
        return "visualize";
    }

    @Override
    public String getDescription() {
        return "Start an algorithm visualization.";
    }

    @Override
    public String getUsage() {
        return "/algo visualize <category> <algorithm>";
    }

    @Override
    public String getPermission() {
        return "algovision.visualize";
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
