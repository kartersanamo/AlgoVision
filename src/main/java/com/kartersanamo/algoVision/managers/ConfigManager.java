package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSettings;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Loads and provides access to plugin configuration (config.yml).
 */
public class ConfigManager {

    private final AlgoVision plugin;
    private FileConfiguration config;

    public ConfigManager(AlgoVision plugin) {
        this.plugin = plugin;
        reload();
    }

    /** Reload config from disk. */
    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    // --- Region limits ---
    public int getRegionMinWidth() { return config.getInt("region.minWidth", 5); }
    public int getRegionMinHeight() { return config.getInt("region.minHeight", 3); }
    public int getRegionMinLength() { return config.getInt("region.minLength", 5); }
    public int getRegionMaxWidth() { return config.getInt("region.maxWidth", 100); }
    public int getRegionMaxHeight() { return config.getInt("region.maxHeight", 256); }
    public int getRegionMaxLength() { return config.getInt("region.maxLength", 100); }
    public int getRegionMaxVolume() { return config.getInt("region.maxVolume", 100000); }

    public int getCleanupIntervalTicks() { return config.getInt("performance.cleanupIntervalTicks", 6000); }

    // --- Data ---
    public int getDataDefaultSize() { return config.getInt("data.defaultSize", 30); }
    public int getDataMaxSize() { return config.getInt("data.maxSize", 100); }
    public int getDataRandomMin() { return config.getInt("data.randomRange.min", 1); }
    public int getDataRandomMax() { return config.getInt("data.randomRange.max", 100); }

    /**
     * Build default VisualizationSettings from config.
     */
    public VisualizationSettings getDefaultSettings() {
        VisualizationSettings s = new VisualizationSettings();
        s.setSpeed(config.getInt("settings.speed", 10));
        s.setDelay(config.getInt("settings.delay", 20));
        s.setHighlightColor(getMaterial("settings.highlightColor", Material.YELLOW_CONCRETE));
        s.setSwapColor(getMaterial("settings.swapColor", Material.RED_CONCRETE));
        s.setSortedColor(getMaterial("settings.sortedColor", Material.GREEN_CONCRETE));
        s.setCompareColor(getMaterial("settings.compareColor", Material.GOLD_BLOCK));
        s.setPauseOnComparison(config.getBoolean("settings.pauseOnComparison", false));
        s.setPauseOnSwap(config.getBoolean("settings.pauseOnSwap", false));
        s.setShowStats(config.getBoolean("settings.showStats", true));
        s.setAutoReset(config.getBoolean("settings.autoReset", true));
        s.setSoundEffects(config.getBoolean("settings.soundEffects", true));
        s.setParticleEffects(config.getBoolean("settings.particleEffects", true));
        String mode = config.getString("settings.displayMode", "HEIGHT");
        try {
            s.setDisplayMode(VisualizationSettings.DisplayMode.valueOf(mode.toUpperCase()));
        } catch (IllegalArgumentException ignored) {
            s.setDisplayMode(VisualizationSettings.DisplayMode.HEIGHT);
        }
        s.setBaseBlock(getMaterial("settings.baseBlock", Material.STONE));
        s.setMinHeight(config.getInt("settings.minHeight", 1));
        s.setMaxHeight(config.getInt("settings.maxHeight", 64));
        return s;
    }

    private Material getMaterial(String path, Material def) {
        String name = config.getString(path);
        if (name == null || name.isEmpty()) return def;
        try {
            return Material.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return def;
        }
    }
}
