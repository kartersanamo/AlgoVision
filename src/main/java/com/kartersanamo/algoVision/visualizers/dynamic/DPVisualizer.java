package com.kartersanamo.algoVision.visualizers.dynamic;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Base for dynamic programming table visualizations.
 */
public abstract class DPVisualizer extends Visualizer {

    protected int[] dp;

    protected DPVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    protected void renderCell(int index) {
        if (index < 0 || index >= dp.length) return;
        Location base = region.getMinPoint().clone().add(index, 0, 0);
        int height = Math.max(1, dp[index]);
        for (int y = 0; y < height; y++) {
            setBlock(base.clone().add(0, y, 0), Material.LAPIS_BLOCK);
        }
    }
}

