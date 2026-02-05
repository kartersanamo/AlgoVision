package com.kartersanamo.algoVision.visualizers.searching;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Jump search visualization over a sorted array.
 */
public class JumpSearchVisualizer extends SearchingVisualizer {

    private int step;
    private int prev = 0;

    public JumpSearchVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        data = plugin.getDataManager().generateSorted(region.getWidth());
        normalizeData();
        renderData();
        step = (int) Math.sqrt(data.length);
        target = data[data.length / 2];
    }

    @Override
    protected void executeStep() {
        if (prev >= data.length) return;
        int curr = Math.min(prev + step, data.length - 1);
        highlightIndex(curr);
        stats.incrementComparisons();
        if (data[curr] < target) {
            prev = curr + 1;
        } else {
            for (int i = prev; i <= curr; i++) {
                highlightIndex(i);
                stats.incrementComparisons();
                if (data[i] == target) {
                    prev = data.length;
                    return;
                }
            }
            prev = data.length;
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || prev >= data.length;
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

