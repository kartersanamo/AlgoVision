package com.kartersanamo.algoVision.models;

import com.kartersanamo.algoVision.visualizers.VisualizerState;

import java.util.UUID;

/**
 * Tracks a running visualization session (player, algorithm, region, settings, state).
 */
public class VisualizationSession {

    private final UUID sessionId;
    private final UUID playerId;
    private final String algorithmName;
    private final Region region;
    private final VisualizationSettings settings;
    private com.kartersanamo.algoVision.visualizers.Visualizer visualizer;
    private final long startTime;

    private VisualizerState state = VisualizerState.IDLE;
    private final AlgorithmStats stats = new AlgorithmStats();

    public VisualizationSession(UUID sessionId, UUID playerId, String algorithmName,
                                Region region, VisualizationSettings settings) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.algorithmName = algorithmName;
        this.region = region;
        this.settings = settings;
        this.startTime = System.currentTimeMillis();
    }

    public void pause() {
        if (visualizer != null) visualizer.pause();
        state = VisualizerState.PAUSED;
    }

    public void resume() {
        if (visualizer != null) visualizer.resume();
        state = VisualizerState.RUNNING;
    }

    public void stop() {
        if (visualizer != null) visualizer.stop();
        state = VisualizerState.STOPPED;
    }

    public void step(int steps) {
        if (visualizer != null) visualizer.step(steps);
    }

    public void setSpeed(double multiplier) {
        if (visualizer != null) visualizer.setSpeed(multiplier);
    }

    public UUID getSessionId() { return sessionId; }
    public UUID getPlayerId() { return playerId; }
    public String getAlgorithmName() { return algorithmName; }
    public Region getRegion() { return region; }
    public VisualizationSettings getSettings() { return settings; }
    public com.kartersanamo.algoVision.visualizers.Visualizer getVisualizer() { return visualizer; }
    public void setVisualizer(com.kartersanamo.algoVision.visualizers.Visualizer visualizer) { this.visualizer = visualizer; }
    public long getStartTime() { return startTime; }
    public VisualizerState getState() { return state; }
    public void setState(VisualizerState state) { this.state = state; }
    public AlgorithmStats getStats() { return stats; }
}
