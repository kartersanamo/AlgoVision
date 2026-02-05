package com.kartersanamo.algoVision.visualizers.searching;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Linear search visualization.
 */
public class LinearSearchVisualizer extends SearchingVisualizer {

    private int index = 0;

    public LinearSearchVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        data = plugin.getDataManager().generateRandom(region.getWidth());
        normalizeData();
        renderData();
        target = data[data.length / 2];
    }

    @Override
    protected void executeStep() {
        if (index >= data.length) return;
        highlightIndex(index);
        stats.incrementComparisons();
        if (data[index] == target) {
            index = data.length; // signal found
        } else {
            index++;
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || index >= data.length;
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

