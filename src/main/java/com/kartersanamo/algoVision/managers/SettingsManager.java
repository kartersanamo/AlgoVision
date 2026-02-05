package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSettings;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Manages global and per-player visualization settings.
 */
public class SettingsManager {

    private final AlgoVision plugin;
    private VisualizationSettings globalSettings;
    private final Map<UUID, VisualizationSettings> playerSettings = new HashMap<>();

    public SettingsManager(AlgoVision plugin) {
        this.plugin = plugin;
        this.globalSettings = plugin.getConfigManager().getDefaultSettings().copy();
    }

    /** Get settings for player (merge player overrides with global). */
    public VisualizationSettings getSettings(Player player) {
        if (player == null) return globalSettings.copy();
        VisualizationSettings playerSpec = playerSettings.get(player.getUniqueId());
        if (playerSpec == null) return globalSettings.copy();
        return playerSpec.copy();
    }

    public VisualizationSettings getGlobalSettings() {
        return globalSettings.copy();
    }

    public void setGlobalSetting(String key, String value) {
        applySetting(globalSettings, key, value);
    }

    public void setPlayerSetting(UUID playerId, String key, String value) {
        VisualizationSettings s = playerSettings.computeIfAbsent(playerId, id -> plugin.getConfigManager().getDefaultSettings().copy());
        applySetting(s, key, value);
    }

    public void resetPlayerSettings(UUID playerId) {
        playerSettings.remove(playerId);
    }

    public void resetGlobalSettings() {
        this.globalSettings = plugin.getConfigManager().getDefaultSettings().copy();
    }

    public List<String> getAvailableSettings() {
        return Arrays.asList("speed", "delay", "highlightColor", "swapColor", "sortedColor", "compareColor",
                "pauseOnComparison", "pauseOnSwap", "showStats", "autoReset", "soundEffects", "particleEffects",
                "displayMode", "baseBlock", "minHeight", "maxHeight");
    }

    public String getSettingValue(VisualizationSettings settings, String key) {
        if (settings == null || key == null) return "";
        key = key.toLowerCase();
        return switch (key) {
            case "speed" -> String.valueOf(settings.getSpeed());
            case "delay" -> String.valueOf(settings.getDelay());
            case "highlightcolor" -> settings.getHighlightColor().name();
            case "swapcolor" -> settings.getSwapColor().name();
            case "sortedcolor" -> settings.getSortedColor().name();
            case "comparecolor" -> settings.getCompareColor().name();
            case "pauseoncomparison" -> String.valueOf(settings.isPauseOnComparison());
            case "pauseonswap" -> String.valueOf(settings.isPauseOnSwap());
            case "showstats" -> String.valueOf(settings.isShowStats());
            case "autoreset" -> String.valueOf(settings.isAutoReset());
            case "soundeffects" -> String.valueOf(settings.isSoundEffects());
            case "particleeffects" -> String.valueOf(settings.isParticleEffects());
            case "displaymode" -> settings.getDisplayMode().name();
            case "baseblock" -> settings.getBaseBlock().name();
            case "minheight" -> String.valueOf(settings.getMinHeight());
            case "maxheight" -> String.valueOf(settings.getMaxHeight());
            default -> "";
        };
    }

    private void applySetting(VisualizationSettings s, String key, String value) {
        if (key == null || value == null) return;
        key = key.toLowerCase();
        switch (key) {
            case "speed": s.setSpeed(parseInt(value, 10)); break;
            case "delay": s.setDelay(parseInt(value, 20)); break;
            case "highlightcolor": s.setHighlightColor(parseMaterial(value, s.getHighlightColor())); break;
            case "swapcolor": s.setSwapColor(parseMaterial(value, s.getSwapColor())); break;
            case "sortedcolor": s.setSortedColor(parseMaterial(value, s.getSortedColor())); break;
            case "comparecolor": s.setCompareColor(parseMaterial(value, s.getCompareColor())); break;
            case "pauseoncomparison": s.setPauseOnComparison(Boolean.parseBoolean(value)); break;
            case "pauseonswap": s.setPauseOnSwap(Boolean.parseBoolean(value)); break;
            case "showstats": s.setShowStats(Boolean.parseBoolean(value)); break;
            case "autoreset": s.setAutoReset(Boolean.parseBoolean(value)); break;
            case "soundeffects": s.setSoundEffects(Boolean.parseBoolean(value)); break;
            case "particleeffects": s.setParticleEffects(Boolean.parseBoolean(value)); break;
            case "displaymode":
                try { s.setDisplayMode(VisualizationSettings.DisplayMode.valueOf(value.toUpperCase())); } catch (Exception ignored) {}
                break;
            case "baseblock": s.setBaseBlock(parseMaterial(value, s.getBaseBlock())); break;
            case "minheight": s.setMinHeight(parseInt(value, 1)); break;
            case "maxheight": s.setMaxHeight(parseInt(value, 64)); break;
        }
    }

    private int parseInt(String v, int def) {
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return def; }
    }

    private org.bukkit.Material parseMaterial(String v, org.bukkit.Material def) {
        try { return org.bukkit.Material.valueOf(v.toUpperCase()); } catch (Exception e) { return def; }
    }
}
