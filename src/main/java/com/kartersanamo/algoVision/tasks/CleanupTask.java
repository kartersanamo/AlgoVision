package com.kartersanamo.algoVision.tasks;

import com.kartersanamo.algoVision.AlgoVision;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Periodic cleanup of old visualization data and sessions.
 */
public class CleanupTask extends BukkitRunnable {

    private final AlgoVision plugin;

    public CleanupTask(AlgoVision plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getVisualizationManager() != null) {
            plugin.getVisualizationManager().cleanupExpiredSessions();
        }
    }
}
