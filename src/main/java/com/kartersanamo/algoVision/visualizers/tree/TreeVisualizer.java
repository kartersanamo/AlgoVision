package com.kartersanamo.algoVision.visualizers.tree;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Base for tree visualizations, mapping nodes into the region.
 */
public abstract class TreeVisualizer extends Visualizer {

    protected TreeVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    protected void highlightTreePosition(int offset) {
        int width = region.getWidth();
        if (offset < 0 || offset >= width) return;
        Location loc = region.getMinPoint().clone().add(offset, 0, 0);
        highlightBlock(loc, Material.OAK_LOG);
    }
}

