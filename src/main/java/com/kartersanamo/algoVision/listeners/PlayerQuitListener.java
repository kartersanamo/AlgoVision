package com.kartersanamo.algoVision.listeners;

import com.kartersanamo.algoVision.AlgoVision;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Cleans up active visualizations when a player quits.
 */
public class PlayerQuitListener implements Listener {

    private final AlgoVision plugin;

    public PlayerQuitListener(AlgoVision plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getVisualizationManager() != null) {
            plugin.getVisualizationManager().cleanupPlayerSessions(player.getUniqueId());
        }
        if (plugin.getRegionManager() != null) {
            plugin.getRegionManager().clearRegion(player);
        }
    }
}
