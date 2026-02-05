package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Selection sort visualization.
 */
public class SelectionSortVisualizer extends SortingVisualizer {

    private int i = 0;
    private int j = 1;
    private int minIndex = 0;

    public SelectionSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void executeStep() {
        if (data == null || i >= data.length - 1) return;
        if (j < data.length) {
            compare(minIndex, j);
            if (data[j] < data[minIndex]) {
                minIndex = j;
            }
            j++;
        } else {
            if (minIndex != i) {
                swap(i, minIndex);
            }
            markSorted(i);
            i++;
            minIndex = i;
            j = i + 1;
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || i >= data.length - 1;
    }

    @Override
    protected void onComplete() {
        if (data != null) {
            for (int k = 0; k < data.length; k++) {
                markSorted(k);
            }
        }
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

