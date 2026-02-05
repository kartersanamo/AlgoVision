package com.kartersanamo.algoVision.visualizers.graph;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.Region;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Base class for graph traversal visualizations.
 */
public abstract class GraphVisualizer extends Visualizer {

    protected GraphVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    protected void highlightPositionInRegion(int index, Material material) {
        Region r = region;
        if (r == null) return;
        int width = r.getWidth();
        int length = r.getLength();
        if (width <= 0 || length <= 0) return;
        int xOffset = index % width;
        int zOffset = index / width;
        if (zOffset >= length) return;
        Location base = r.getMinPoint().clone().add(xOffset, 0, zOffset);
        highlightBlock(base, material);
    }
}

