package com.kartersanamo.algoVision.models;

/**
 * Tracks statistics for a single algorithm execution.
 */
public class AlgorithmStats {

    private int comparisons;
    private int swaps;
    private int arrayAccesses;
    private long durationMs;
    private int steps;

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementSwaps() {
        swaps++;
    }

    public void incrementArrayAccesses() {
        arrayAccesses++;
    }

    public void recordDuration(long ms) {
        this.durationMs = ms;
    }

    public void recordSteps(int steps) {
        this.steps = steps;
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getSwaps() {
        return swaps;
    }

    public int getArrayAccesses() {
        return arrayAccesses;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public int getSteps() {
        return steps;
    }
}
