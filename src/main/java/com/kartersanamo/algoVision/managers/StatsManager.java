package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.models.AlgorithmStats;

import java.util.*;

/**
 * Records and exposes history and leaderboards for algorithm runs.
 * (Persistence stubbed; can be extended to save to disk.)
 */
public class StatsManager {

    private final List<AlgorithmStats> history = new ArrayList<>();
    private final Map<String, List<AlgorithmStats>> algorithmLeaderboards = new HashMap<>();

    public void recordStats(String algorithmName, AlgorithmStats stats) {
        if (algorithmName == null || stats == null) {
            return;
        }
        history.add(stats);
        algorithmLeaderboards
                .computeIfAbsent(algorithmName.toLowerCase(), k -> new ArrayList<>())
                .add(stats);
    }

    public List<AlgorithmStats> getHistory(int limit) {
        int size = history.size();
        if (limit <= 0 || limit >= size) {
            return new ArrayList<>(history);
        }
        return new ArrayList<>(history.subList(size - limit, size));
    }

    public List<AlgorithmStats> getLeaderboard(String algorithm, int limit) {
        if (algorithm == null) return List.of();
        List<AlgorithmStats> list = algorithmLeaderboards.getOrDefault(algorithm.toLowerCase(), List.of());
        List<AlgorithmStats> copy = new ArrayList<>(list);
        copy.sort(Comparator.comparingLong(AlgorithmStats::getDurationMs));
        if (limit > 0 && copy.size() > limit) {
            return new ArrayList<>(copy.subList(0, limit));
        }
        return copy;
    }

    public void saveIfNeeded() {
        // Placeholder hook for future persistence.
    }
}
