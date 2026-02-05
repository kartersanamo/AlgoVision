package com.kartersanamo.algoVision.visualizers.pathfinding;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple A* style frontier expansion along a diagonal for demonstration.
 */
public class AStarVisualizer extends PathfindingVisualizer {

    private int step = 0;
    private final int maxSteps;
    private final Set<String> visited = new HashSet<>();

    public AStarVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
        this.maxSteps = Math.min(region.getWidth(), region.getLength());
    }

    @Override
    protected void initialize() {
        step = 0;
    }

    @Override
    protected void executeStep() {
        if (step >= maxSteps) return;
        int x = step;
        int z = step;
        String key = x + ":" + z;
        if (!visited.contains(key)) {
            visited.add(key);
            highlightBlock(cellToLocation(x, z), Material.REDSTONE_BLOCK);
            stats.incrementArrayAccesses();
        }
        step++;
    }

    @Override
    protected boolean isComplete() {
        return step >= maxSteps;
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

