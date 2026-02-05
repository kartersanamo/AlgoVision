package com.kartersanamo.algoVision;

import com.kartersanamo.algoVision.commands.AlgoCommand.AlgoCommand;
import com.kartersanamo.algoVision.listeners.BlockBreakListener;
import com.kartersanamo.algoVision.listeners.PlayerInteractListener;
import com.kartersanamo.algoVision.listeners.PlayerQuitListener;
import com.kartersanamo.algoVision.managers.*;
import com.kartersanamo.algoVision.tasks.CleanupTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main plugin class for AlgoVision - Minecraft algorithm visualization.
 */
public class AlgoVision extends JavaPlugin {

    private ConfigManager configManager;
    private RegionManager regionManager;
    private SettingsManager settingsManager;
    private VisualizationManager visualizationManager;
    private PresetManager presetManager;
    private DataManager dataManager;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        regionManager = new RegionManager(this);
        settingsManager = new SettingsManager(this);
        visualizationManager = new VisualizationManager();
        presetManager = new PresetManager();
        dataManager = new DataManager(this);
        statsManager = new StatsManager();

        AlgoCommand algoCommand = new AlgoCommand(this);
        Objects.requireNonNull(getCommand("algo")).setExecutor(algoCommand);
        Objects.requireNonNull(getCommand("algo")).setTabCompleter(algoCommand);

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);

        new CleanupTask(this).runTaskTimer(this, 100L, configManager.getCleanupIntervalTicks());

        getLogger().info("AlgoVision has been enabled!");
    }

    @Override
    public void onDisable() {
        if (visualizationManager != null) {
            visualizationManager.stopAll();
        }
        if (statsManager != null) {
            statsManager.saveIfNeeded();
        }
        if (presetManager != null) {
            presetManager.saveIfNeeded();
        }
        getLogger().info("AlgoVision has been disabled!");
    }

    public ConfigManager getConfigManager() { return configManager; }
    public RegionManager getRegionManager() { return regionManager; }
    public SettingsManager getSettingsManager() { return settingsManager; }
    public VisualizationManager getVisualizationManager() { return visualizationManager; }
    public PresetManager getPresetManager() { return presetManager; }
    public DataManager getDataManager() { return dataManager; }
    public StatsManager getStatsManager() { return statsManager; }
}
