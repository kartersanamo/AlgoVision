package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.models.VisualizationPreset;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages named visualization presets (in-memory, with hooks for persistence).
 */
public class PresetManager {

    private final Map<String, VisualizationPreset> presets = new HashMap<>();

    public void savePreset(String name, VisualizationPreset preset) {
        if (name == null || preset == null) return;
        presets.put(name.toLowerCase(), preset);
    }

    public VisualizationPreset getPreset(String name) {
        if (name == null) return null;
        return presets.get(name.toLowerCase());
    }

    public void deletePreset(String name) {
        if (name == null) return;
        presets.remove(name.toLowerCase());
    }

    public Map<String, VisualizationPreset> getAllPresets() {
        return new HashMap<>(presets);
    }

    public void saveIfNeeded() {
        // Placeholder for persistence to disk (YAML/JSON) in later phases.
    }
}
