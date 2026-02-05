package com.kartersanamo.algoVision.visualizers.graph;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Very simple Dijkstra-like visualization over a grid where each step expands frontier.
 */
public class DijkstraVisualizer extends GraphVisualizer {

    private final double[] dist;
    private final boolean[] visited;

    public DijkstraVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
        int size = session.getRegion().getWidth() * session.getRegion().getLength();
        this.dist = new double[size];
        this.visited = new boolean[size];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
    }

    @Override
    protected void initialize() {
        dist[0] = 0;
    }

    @Override
    protected void executeStep() {
        int n = dist.length;
        int best = -1;
        double bestDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            if (!visited[i] && dist[i] < bestDist) {
                bestDist = dist[i];
                best = i;
            }
        }
        if (best == -1) return;
        int current = best;
        visited[current] = true;
        highlightPositionInRegion(current, Material.EMERALD_BLOCK);
        stats.incrementArrayAccesses();
    }

    @Override
    protected boolean isComplete() {
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

