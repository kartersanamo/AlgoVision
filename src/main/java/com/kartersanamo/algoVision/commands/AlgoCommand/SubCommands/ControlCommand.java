package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.VisualizationManager;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ControlCommand extends BaseSubCommand {

    private final VisualizationManager visualizationManager;

    public ControlCommand(AlgoVision plugin) {
        super(plugin);
        this.visualizationManager = plugin.getVisualizationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            sendError(sender, "Only players can control visualizations.");
            return true;
        }
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to control visualizations.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            sendInfo(sender, "Usage: " + getUsage());
            return true;
        }

        String action = args[0].toLowerCase();
        switch (action) {
            case "list":
                handleList(player);
                break;
            case "pause":
                handlePauseResume(player, args, true);
                break;
            case "resume":
                handlePauseResume(player, args, false);
                break;
            case "stop":
                handleStop(player, args);
                break;
            case "step":
                handleStep(player, args);
                break;
            default:
                sendError(sender, "Unknown action: " + action);
                sendInfo(sender, "Usage: " + getUsage());
        }
        return true;
    }

    private void handleList(Player player) {
        List<VisualizationSession> sessions = visualizationManager.getPlayerSessions(player.getUniqueId());
        if (sessions.isEmpty()) {
            sendInfo(player, "You have no active visualizations.");
            return;
        }
        player.sendMessage("§6§lYour visualizations:");
        for (VisualizationSession s : sessions) {
            player.sendMessage(" §e" + s.getSessionId() + "§7 - §f" + s.getAlgorithmName()
                    + " §7[" + s.getState().name() + "]");
        }
    }

    private VisualizationSession resolveSession(Player player, String[] args) {
        if (args.length > 1) {
            try {
                UUID id = UUID.fromString(args[1]);
                VisualizationSession s = visualizationManager.getSession(id);
                if (s != null && s.getPlayerId().equals(player.getUniqueId())) {
                    return s;
                }
            } catch (IllegalArgumentException ignored) {
            }
            sendError(player, "Invalid or unknown session id.");
            return null;
        } else {
            VisualizationSession s = visualizationManager.getMostRecentSession(player.getUniqueId());
            if (s == null) {
                sendError(player, "You have no active visualizations.");
            }
            return s;
        }
    }

    private void handlePauseResume(Player player, String[] args, boolean pause) {
        VisualizationSession s = resolveSession(player, args);
        if (s == null) return;
        if (pause) {
            visualizationManager.pauseVisualization(s.getSessionId());
            sendSuccess(player, "Paused visualization " + s.getSessionId());
        } else {
            visualizationManager.resumeVisualization(s.getSessionId());
            sendSuccess(player, "Resumed visualization " + s.getSessionId());
        }
    }

    private void handleStop(Player player, String[] args) {
        VisualizationSession s = resolveSession(player, args);
        if (s == null) return;
        visualizationManager.stopVisualization(s.getSessionId());
        sendSuccess(player, "Stopped visualization " + s.getSessionId());
    }

    private void handleStep(Player player, String[] args) {
        VisualizationSession s = resolveSession(player, args);
        if (s == null) return;
        int steps = 1;
        if (args.length > 2) {
            try {
                steps = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {
            }
        }
        visualizationManager.stepVisualization(s.getSessionId(), steps);
        sendSuccess(player, "Stepped visualization " + s.getSessionId() + " by " + steps + " steps.");
    }

    @Override
    public String getName() {
        return "control";
    }

    @Override
    public String getDescription() {
        return "Control active visualizations (pause/resume/stop/list).";
    }

    @Override
    public String getUsage() {
        return "/algo control <list|pause|resume|stop|step> [sessionId] [steps]";
    }

    @Override
    public String getPermission() {
        return "algovision.control";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            for (String s : new String[]{"list", "pause", "resume", "stop", "step"}) {
                if (s.startsWith(partial)) suggestions.add(s);
            }
        }
        return suggestions;
    }
}
