package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Insertion sort visualization.
 */
public class InsertionSortVisualizer extends SortingVisualizer {

    private int i = 1;
    private int j = 1;
    private int key;

    public InsertionSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        super.initialize();
        if (data != null && data.length > 1) {
            key = data[i];
        }
    }

    @Override
    protected void executeStep() {
        if (data == null || i >= data.length) return;
        if (j > 0 && data[j - 1] > key) {
            compare(j - 1, j);
            data[j] = data[j - 1];
            j--;
        } else {
            data[j] = key;
            renderData();
            i++;
            if (i < data.length) {
                key = data[i];
                j = i;
            }
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || i >= data.length;
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

