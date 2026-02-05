package com.kartersanamo.algoVision.visualizers.pathfinding;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import org.bukkit.Location;

/**
 * Base for grid-based pathfinding visualizations.
 */
public abstract class PathfindingVisualizer extends Visualizer {

    protected PathfindingVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    protected Location cellToLocation(int x, int z) {
        return region.getMinPoint().clone().add(x, 0, z);
    }
}

