package com.kartersanamo.algoVision.visualizers.sorting;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Heap sort visualization (simplified).
 */
public class HeapSortVisualizer extends SortingVisualizer {

    private int heapSize;
    private int i;
    private boolean building = true;

    public HeapSortVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        super.initialize();
        heapSize = data.length;
        i = heapSize / 2 - 1;
    }

    @Override
    protected void executeStep() {
        if (data == null || data.length <= 1) return;
        if (building) {
            if (i >= 0) {
                heapify(heapSize, i);
                i--;
            } else {
                building = false;
                i = heapSize - 1;
            }
        } else {
            if (i <= 0) return;
            swap(0, i);
            markSorted(i);
            heapSize--;
            heapify(heapSize, 0);
            i--;
        }
    }

    private void heapify(int n, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < n && data[left] > data[largest]) largest = left;
        if (right < n && data[right] > data[largest]) largest = right;

        if (largest != root) {
            swap(root, largest);
            heapify(n, largest);
        }
    }

    @Override
    protected boolean isComplete() {
        return data == null || (!building && i <= 0);
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

