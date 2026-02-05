package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Quick sort visualization (simple recursive implementation, multiple operations per tick).
 */
public class QuickSortVisualizer extends SortingVisualizer {

    private int[] stackL;
    private int[] stackR;
    private int stackSize;

    public QuickSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        super.initialize();
        int n = data.length;
        stackL = new int[n];
        stackR = new int[n];
        stackSize = 0;
        push(0, n - 1);
    }

    private void push(int l, int r) {
        stackL[stackSize] = l;
        stackR[stackSize] = r;
        stackSize++;
    }

    private void pop() {
        if (stackSize > 0) {
            stackSize--;
        }
    }

    @Override
    protected void executeStep() {
        if (stackSize == 0 || data == null) {
            return;
        }
        int l = stackL[stackSize - 1];
        int r = stackR[stackSize - 1];
        pop();

        if (l >= r) {
            return;
        }

        int i = l;
        int j = r;
        int pivot = data[(l + r) / 2];

        while (i <= j) {
            while (data[i] < pivot) i++;
            while (data[j] > pivot) j--;
            if (i <= j) {
                compare(i, j);
                if (data[i] != data[j]) {
                    swap(i, j);
                }
                i++;
                j--;
            }
        }

        if (l < j) push(l, j);
        if (i < r) push(i, r);
    }

    @Override
    protected boolean isComplete() {
        return data == null || stackSize == 0;
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
