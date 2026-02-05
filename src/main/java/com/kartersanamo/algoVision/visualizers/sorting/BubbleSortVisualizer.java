package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Bubble sort visualization.
 */
public class BubbleSortVisualizer extends SortingVisualizer {

    private int i = 0;
    private int j = 0;

    public BubbleSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void executeStep() {
        if (data == null || data.length <= 1) {
            return;
        }

        if (j >= data.length - i - 1) {
            markSorted(data.length - i - 1);
            i++;
            j = 0;
            if (i >= data.length - 1) {
                return;
            }
        }

        compare(j, j + 1);
        if (data[j] > data[j + 1]) {
            swap(j, j + 1);
        }

        j++;
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
