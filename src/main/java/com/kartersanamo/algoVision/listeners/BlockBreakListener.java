package com.kartersanamo.algoVision.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Protects blocks used in active visualizations (placeholder, refined in later phases).
 */
public class BlockBreakListener implements Listener {

    public BlockBreakListener() {
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Logic to prevent breaking blocks in active visualization regions
        // will be implemented when VisualizationManager tracking is complete.
    }
}
