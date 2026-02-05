package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.SettingsManager;
import com.kartersanamo.algoVision.models.VisualizationSettings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SettingsCommand extends BaseSubCommand {

    private final SettingsManager settingsManager;

    public SettingsCommand(AlgoVision plugin) {
        super(plugin);
        this.settingsManager = plugin.getSettingsManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendInfo(sender, "Usage: " + getUsage());
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "list":
                sender.sendMessage("§6Available settings: §f" + String.join(", ", settingsManager.getAvailableSettings()));
                return true;
            case "get":
                return handleGet(sender, args);
            case "set":
                return handleSet(sender, args);
            case "reset":
                return handleReset(sender, args);
            default:
                sendError(sender, "Unknown subcommand: " + sub);
                sendInfo(sender, "Usage: " + getUsage());
                return true;
        }
    }

    private boolean handleGet(CommandSender sender, String[] args) {
        boolean global = args.length >= 3 && args[2].equalsIgnoreCase("global");
        if (global) {
            if (!sender.hasPermission("algovision.settings.global")) {
                sendError(sender, "You do not have permission to view global settings.");
                return global;
            }
        }
        if (args.length < 2) {
            sendError(sender, "Usage: /algo settings get <key> [global]");
            return global;
        }
        String key = args[1];
        VisualizationSettings s;
        if (global) {
            s = settingsManager.getGlobalSettings();
        } else {
            if (!(sender instanceof Player)) {
                sendError(sender, "Player-only unless using global.");
                return true;
            }
            s = settingsManager.getSettings((Player) sender);
        }
        String value = settingsManager.getSettingValue(s, key);
        sendInfo(sender, key + " = " + value);
        return true;
    }

    private boolean handleSet(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sendError(sender, "Usage: /algo settings set <key> <value> [global]");
            return false;
        }
        String key = args[1];
        String value = args[2];
        boolean global = args.length >= 4 && args[3].equalsIgnoreCase("global");

        if (global) {
            if (!sender.hasPermission("algovision.settings.global")) {
                sendError(sender, "You do not have permission to change global settings.");
                return true;
            }
            settingsManager.setGlobalSetting(key, value);
            sendSuccess(sender, "Global setting " + key + " set to " + value);
        } else {
            if (!(sender instanceof Player player)) {
                sendError(sender, "Player-only unless using global.");
                return true;
            }
            settingsManager.setPlayerSetting(player.getUniqueId(), key, value);
            sendSuccess(sender, "Your setting " + key + " set to " + value);
        }
        return true;
    }

    private boolean handleReset(CommandSender sender, String[] args) {
        boolean global = args.length >= 2 && args[1].equalsIgnoreCase("global");
        if (global) {
            if (!sender.hasPermission("algovision.settings.global")) {
                sendError(sender, "You do not have permission to reset global settings.");
                return global;
            }
            settingsManager.resetGlobalSettings();
            sendSuccess(sender, "Global settings reset to defaults.");
        } else {
            if (!(sender instanceof Player player)) {
                sendError(sender, "Player-only unless using global.");
                return true;
            }
            settingsManager.resetPlayerSettings(player.getUniqueId());
            sendSuccess(sender, "Your settings have been reset.");
        }
        return true;
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return "View and change visualization settings.";
    }

    @Override
    public String getUsage() {
        return "/algo settings <list|get|set|reset> ...";
    }

    @Override
    public String getPermission() {
        return "algovision.settings";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            for (String s : new String[]{"list", "get", "set", "reset"}) {
                if (s.startsWith(partial)) suggestions.add(s);
            }
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("set"))) {
            String partial = args[1].toLowerCase();
            for (String key : settingsManager.getAvailableSettings()) {
                if (key.toLowerCase().startsWith(partial)) suggestions.add(key);
            }
        }
        return suggestions;
    }
}
