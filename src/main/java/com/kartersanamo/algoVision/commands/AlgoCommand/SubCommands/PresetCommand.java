package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.PresetManager;
import com.kartersanamo.algoVision.managers.SettingsManager;
import com.kartersanamo.algoVision.models.VisualizationPreset;
import com.kartersanamo.algoVision.models.VisualizationSettings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PresetCommand extends BaseSubCommand {

    private final PresetManager presetManager;
    private final SettingsManager settingsManager;

    public PresetCommand(AlgoVision plugin) {
        super(plugin);
        this.presetManager = plugin.getPresetManager();
        this.settingsManager = plugin.getSettingsManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sendError(sender, "Only players can use presets.");
            return true;
        }
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to use presets.");
            return true;
        }

        if (args.length == 0) {
            sendInfo(sender, "Usage: " + getUsage());
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "save":
                return handleSave((Player) sender, args);
            case "load":
                return handleLoad((Player) sender, args);
            case "delete":
                return handleDelete(sender, args);
            case "list":
                return handleList(sender);
            default:
                sendError(sender, "Unknown subcommand: " + sub);
                sendInfo(sender, "Usage: " + getUsage());
                return true;
        }
    }

    private boolean handleSave(Player player, String[] args) {
        if (args.length < 2) {
            sendError(player, "Usage: /algo preset save <name>");
            return true;
        }
        String name = args[1];
        VisualizationSettings settings = settingsManager.getSettings(player);
        VisualizationPreset preset = new VisualizationPreset(name, settings);
        presetManager.savePreset(name, preset);
        sendSuccess(player, "Preset '" + name + "' saved.");
        return true;
    }

    private boolean handleLoad(Player player, String[] args) {
        if (args.length < 2) {
            sendError(player, "Usage: /algo preset load <name>");
            return false;
        }
        String name = args[1];
        VisualizationPreset preset = presetManager.getPreset(name);
        if (preset == null) {
            sendError(player, "Preset '" + name + "' not found.");
            return true;
        }
        VisualizationSettings settings = preset.getSettings();
        // Apply all settings to player-specific settings by setting each key
        for (String key : settingsManager.getAvailableSettings()) {
            String value = settingsManager.getSettingValue(settings, key);
            settingsManager.setPlayerSetting(player.getUniqueId(), key, value);
        }
        sendSuccess(player, "Preset '" + name + "' loaded.");
        return true;
    }

    private boolean handleDelete(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sendError(sender, "Usage: /algo preset delete <name>");
            return true;
        }
        String name = args[1];
        presetManager.deletePreset(name);
        sendSuccess(sender, "Preset '" + name + "' deleted (if it existed).");
        return true;
    }

    private boolean handleList(CommandSender sender) {
        if (presetManager.getAllPresets().isEmpty()) {
            sendInfo(sender, "No presets saved.");
            return true;
        }
        sender.sendMessage("§6Saved presets:");
        for (String name : presetManager.getAllPresets().keySet()) {
            sender.sendMessage(" §e" + name);
        }
        return true;
    }

    @Override
    public String getName() {
        return "preset";
    }

    @Override
    public String getDescription() {
        return "Manage visualization presets.";
    }

    @Override
    public String getUsage() {
        return "/algo preset <save|load|delete|list> ...";
    }

    @Override
    public String getPermission() {
        return "algovision.preset";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            for (String s : new String[]{"save", "load", "delete", "list"}) {
                if (s.startsWith(partial)) suggestions.add(s);
            }
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("load") || args[0].equalsIgnoreCase("delete"))) {
            String partial = args[1].toLowerCase();
            for (String name : presetManager.getAllPresets().keySet()) {
                if (name.toLowerCase().startsWith(partial)) suggestions.add(name);
            }
        }
        return suggestions;
    }
}
