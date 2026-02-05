package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Shell sort visualization.
 */
public class ShellSortVisualizer extends SortingVisualizer {

    private int gap;
    private int i;
    private int j;
    private int temp;
    private boolean innerActive = false;

    public ShellSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        super.initialize();
        gap = data.length / 2;
        i = gap;
    }

    @Override
    protected void executeStep() {
        if (data == null || gap == 0) return;
        if (!innerActive) {
            if (i < data.length) {
                temp = data[i];
                j = i;
                innerActive = true;
            } else {
                gap /= 2;
                i = gap;
            }
        } else {
            if (j >= gap && data[j - gap] > temp) {
                compare(j - gap, j);
                data[j] = data[j - gap];
                j -= gap;
            } else {
                data[j] = temp;
                innerActive = false;
                i++;
                renderData();
            }
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || gap == 0;
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

