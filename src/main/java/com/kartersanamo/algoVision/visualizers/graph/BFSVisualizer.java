package com.kartersanamo.algoVision.visualizers.graph;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Simple BFS traversal visualization over a grid representation of the region.
 */
public class BFSVisualizer extends GraphVisualizer {

    private final Queue<Integer> queue = new ArrayDeque<>();
    private final boolean[] visited;

    public BFSVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
        int size = session.getRegion().getWidth() * session.getRegion().getLength();
        this.visited = new boolean[size];
    }

    @Override
    protected void initialize() {
        queue.add(0);
        visited[0] = true;
    }

    @Override
    protected void executeStep() {
        if (queue.isEmpty()) return;
        int idx = queue.poll();
        highlightPositionInRegion(idx, settings.getHighlightColor());
        stats.incrementArrayAccesses();

        int width = region.getWidth();
        int length = region.getLength();
        int x = idx % width;
        int z = idx / width;
        enqueueIfValid(x + 1, z, width, length);
        enqueueIfValid(x - 1, z, width, length);
        enqueueIfValid(x, z + 1, width, length);
        enqueueIfValid(x, z - 1, width, length);
    }

    private void enqueueIfValid(int x, int z, int width, int length) {
        if (x < 0 || z < 0 || x >= width || z >= length) return;
        int idx = z * width + x;
        if (!visited[idx]) {
            visited[idx] = true;
            queue.add(idx);
        }
    }

    @Override
    protected boolean isComplete() {
        return queue.isEmpty();
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

