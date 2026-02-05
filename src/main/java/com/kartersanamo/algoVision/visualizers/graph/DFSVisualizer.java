package com.kartersanamo.algoVision.visualizers.graph;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Stack;

/**
 * Simple DFS traversal visualization over a grid representation of the region.
 */
public class DFSVisualizer extends GraphVisualizer {

    private final Stack<Integer> stack = new Stack<>();
    private final boolean[] visited;

    public DFSVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
        int size = session.getRegion().getWidth() * session.getRegion().getLength();
        this.visited = new boolean[size];
    }

    @Override
    protected void initialize() {
        stack.push(0);
    }

    @Override
    protected void executeStep() {
        if (stack.isEmpty()) return;
        int idx = stack.pop();
        if (visited[idx]) return;
        visited[idx] = true;
        highlightPositionInRegion(idx, Material.BLUE_CONCRETE);
        stats.incrementArrayAccesses();

        int width = region.getWidth();
        int length = region.getLength();
        int x = idx % width;
        int z = idx / width;
        pushIfValid(x + 1, z, width, length);
        pushIfValid(x - 1, z, width, length);
        pushIfValid(x, z + 1, width, length);
        pushIfValid(x, z - 1, width, length);
    }

    private void pushIfValid(int x, int z, int width, int length) {
        if (x < 0 || z < 0 || x >= width || z >= length) return;
        int idx = z * width + x;
        if (!visited[idx]) {
            stack.push(idx);
        }
    }

    @Override
    protected boolean isComplete() {
        return stack.isEmpty();
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

