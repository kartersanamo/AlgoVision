package com.kartersanamo.algoVision.models;

/**
 * Snapshot of visualization settings that can be saved and reused.
 */
public class VisualizationPreset {

    private final String name;
    private final VisualizationSettings settings;

    public VisualizationPreset(String name, VisualizationSettings settings) {
        this.name = name;
        this.settings = settings.copy();
    }

    public String getName() {
        return name;
    }

    public VisualizationSettings getSettings() {
        return settings.copy();
    }
}
