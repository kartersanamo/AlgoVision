package com.kartersanamo.algoVision.visualizers.searching;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import org.bukkit.Location;

import java.util.Arrays;

/**
 * Base class for searching algorithm visualizers.
 */
public abstract class SearchingVisualizer extends Visualizer {

    protected int target;

    protected SearchingVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    /**
     * Scale data values to fit within the region's configured height range.
     */
    protected void normalizeData() {
        if (data == null || data.length == 0) return;
        int min = Arrays.stream(data).min().orElse(0);
        int max = Arrays.stream(data).max().orElse(0);
        int range = Math.max(1, max - min);
        int targetRange = settings.getMaxHeight() - settings.getMinHeight();

        for (int i = 0; i < data.length; i++) {
            data[i] = settings.getMinHeight() +
                    ((data[i] - min) * targetRange) / range;
        }
    }

    /**
     * Clear the region and render the current data as pillars.
     */
    protected void renderData() {
        if (data == null) return;
        clearRegion();
        for (int i = 0; i < data.length; i++) {
            setPillarHeight(i, data[i]);
        }
    }

    protected void highlightIndex(int index) {
        if (data == null || index < 0 || index >= data.length) return;
        Location loc = getBaseLocation(index).add(0, data[index] - 1, 0);
        highlightBlock(loc, settings.getHighlightColor());
    }
}
