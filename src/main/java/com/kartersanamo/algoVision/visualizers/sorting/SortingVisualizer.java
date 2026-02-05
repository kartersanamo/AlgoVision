package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.Visualizer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Arrays;

/**
 * Base class for sorting algorithm visualizers.
 */
public abstract class SortingVisualizer extends Visualizer {

    protected int compareIndex1 = -1;
    protected int compareIndex2 = -1;

    protected SortingVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        Bukkit.getPlayer(session.getPlayerId());
        if (plugin.getDataManager().getData(session.getPlayerId()) != null) {
            data = plugin.getDataManager().getData(session.getPlayerId());
        } else {
            data = plugin.getDataManager().generateRandom(region.getWidth());
        }
        normalizeData();
        renderData();
    }

    protected void normalizeData() {
        int min = Arrays.stream(data).min().orElse(0);
        int max = Arrays.stream(data).max().orElse(0);
        int range = Math.max(1, max - min);
        int targetRange = settings.getMaxHeight() - settings.getMinHeight();

        for (int i = 0; i < data.length; i++) {
            data[i] = settings.getMinHeight() +
                    ((data[i] - min) * targetRange) / range;
        }
    }

    protected void renderData() {
        clearRegion();
        for (int i = 0; i < data.length; i++) {
            setPillarHeight(i, data[i]);
        }
    }

    protected void compare(int i, int j) {
        stats.incrementComparisons();
        compareIndex1 = i;
        compareIndex2 = j;

        Location loc1 = getBaseLocation(i).add(0, data[i] - 1, 0);
        Location loc2 = getBaseLocation(j).add(0, data[j] - 1, 0);

        highlightBlock(loc1, settings.getCompareColor());
        highlightBlock(loc2, settings.getCompareColor());
    }

    protected void swap(int i, int j) {
        stats.incrementSwaps();

        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;

        Location loc1 = getBaseLocation(i);
        Location loc2 = getBaseLocation(j);

        clearPillar(loc1, Math.max(data[i], data[j]));
        clearPillar(loc2, Math.max(data[i], data[j]));

        setPillarHeight(i, data[i]);
        setPillarHeight(j, data[j]);

        highlightBlock(loc1.clone().add(0, data[i] - 1, 0), settings.getSwapColor());
        highlightBlock(loc2.clone().add(0, data[j] - 1, 0), settings.getSwapColor());
    }

    protected void clearPillar(Location base, int maxHeight) {
        for (int y = 0; y < maxHeight; y++) {
            setBlock(base.clone().add(0, y, 0), Material.AIR);
        }
    }

    protected void markSorted(int index) {
        Location top = getBaseLocation(index).add(0, data[index] - 1, 0);
        setBlock(top, settings.getSortedColor());
    }
}
