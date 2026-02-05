package com.kartersanamo.algoVision.visualizers.dynamic;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Simple DP Fibonacci visualization.
 */
public class FibonacciVisualizer extends DPVisualizer {

    private int index = 0;

    public FibonacciVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        int n = Math.max(5, Math.min(region.getWidth(), 20));
        dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        renderCell(0);
        renderCell(1);
        index = 2;
    }

    @Override
    protected void executeStep() {
        if (index >= dp.length) return;
        dp[index] = dp[index - 1] + dp[index - 2];
        stats.incrementArrayAccesses();
        renderCell(index);
        index++;
    }

    @Override
    protected boolean isComplete() {
        return index >= dp.length;
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

