package com.kartersanamo.algoVision.visualizers.searching;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Binary search visualization over a sorted array.
 */
public class BinarySearchVisualizer extends SearchingVisualizer {

    private int left = 0;
    private int right;

    public BinarySearchVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        // Reuse DataManager to generate sorted data
        data = plugin.getDataManager().generateSorted(region.getWidth());
        normalizeData();
        renderData();
        right = data.length - 1;
        target = data[data.length / 2]; // arbitrary target present in array
    }

    @Override
    protected void executeStep() {
        if (left > right || data == null) return;
        int mid = (left + right) / 2;
        highlightIndex(mid);
        stats.incrementComparisons();

        if (data[mid] == target) {
            right = left - 1; // force completion
        } else if (data[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || left > right;
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}
