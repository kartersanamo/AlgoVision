package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Merge sort visualization (iterative bottom-up; multiple operations per tick).
 */
public class MergeSortVisualizer extends SortingVisualizer {

    private int currSize = 1;
    private int leftStart = 0;

    public MergeSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void executeStep() {
        if (data == null || data.length <= 1) return;

        int n = data.length;
        if (currSize >= n) {
            return;
        }

        int mid = Math.min(leftStart + currSize - 1, n - 1);
        int rightEnd = Math.min(leftStart + 2 * currSize - 1, n - 1);

        merge(leftStart, mid, rightEnd);

        leftStart += 2 * currSize;
        if (leftStart >= n - 1) {
            currSize *= 2;
            leftStart = 0;
        }
    }

    private void merge(int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(data, l, L, 0, n1);
        System.arraycopy(data, m + 1, R, 0, n2);

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            compare(l + i, m + 1 + j);
            if (L[i] <= R[j]) {
                data[k] = L[i++];
            } else {
                data[k] = R[j++];
            }
            k++;
        }
        while (i < n1) {
            data[k++] = L[i++];
        }
        while (j < n2) {
            data[k++] = R[j++];
        }

        renderData();
    }

    @Override
    protected boolean isComplete() {
        return data == null || currSize >= data.length;
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
