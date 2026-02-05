package com.kartersanamo.algoVision.models;

import org.bukkit.Material;

/**
 * Settings for algorithm visualization: speed, colors, effects, and display mode.
 */
public class VisualizationSettings {

    public enum DisplayMode {
        HEIGHT,  // Use block height to represent value
        COLOR,   // Use color/material to represent value
        BOTH     // Use both height and color
    }

    // Speed & Timing
    private int speed = 10;
    private int delay = 20;

    // Colors
    private Material highlightColor = Material.YELLOW_CONCRETE;
    private Material swapColor = Material.RED_CONCRETE;
    private Material sortedColor = Material.GREEN_CONCRETE;
    private Material compareColor = Material.GOLD_BLOCK;

    // Behavior
    private boolean pauseOnComparison = false;
    private boolean pauseOnSwap = false;
    private boolean showStats = true;
    private boolean autoReset = true;

    // Effects
    private boolean soundEffects = true;
    private boolean particleEffects = true;

    // Display
    private DisplayMode displayMode = DisplayMode.HEIGHT;
    private Material baseBlock = Material.STONE;
    private int minHeight = 1;
    private int maxHeight = 64;

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = Math.max(1, speed); }
    public int getDelay() { return delay; }
    public void setDelay(int delay) { this.delay = Math.max(0, delay); }

    public Material getHighlightColor() { return highlightColor; }
    public void setHighlightColor(Material highlightColor) { this.highlightColor = highlightColor; }
    public Material getSwapColor() { return swapColor; }
    public void setSwapColor(Material swapColor) { this.swapColor = swapColor; }
    public Material getSortedColor() { return sortedColor; }
    public void setSortedColor(Material sortedColor) { this.sortedColor = sortedColor; }
    public Material getCompareColor() { return compareColor; }
    public void setCompareColor(Material compareColor) { this.compareColor = compareColor; }

    public boolean isPauseOnComparison() { return pauseOnComparison; }
    public void setPauseOnComparison(boolean pauseOnComparison) { this.pauseOnComparison = pauseOnComparison; }
    public boolean isPauseOnSwap() { return pauseOnSwap; }
    public void setPauseOnSwap(boolean pauseOnSwap) { this.pauseOnSwap = pauseOnSwap; }
    public boolean isShowStats() { return showStats; }
    public void setShowStats(boolean showStats) { this.showStats = showStats; }
    public boolean isAutoReset() { return autoReset; }
    public void setAutoReset(boolean autoReset) { this.autoReset = autoReset; }

    public boolean isSoundEffects() { return soundEffects; }
    public void setSoundEffects(boolean soundEffects) { this.soundEffects = soundEffects; }
    public boolean isParticleEffects() { return particleEffects; }
    public void setParticleEffects(boolean particleEffects) { this.particleEffects = particleEffects; }

    public DisplayMode getDisplayMode() { return displayMode; }
    public void setDisplayMode(DisplayMode displayMode) { this.displayMode = displayMode; }
    public Material getBaseBlock() { return baseBlock; }
    public void setBaseBlock(Material baseBlock) { this.baseBlock = baseBlock; }
    public int getMinHeight() { return minHeight; }
    public void setMinHeight(int minHeight) { this.minHeight = Math.max(1, minHeight); }
    public int getMaxHeight() { return maxHeight; }
    public void setMaxHeight(int maxHeight) { this.maxHeight = Math.max(1, maxHeight); }

    /** Returns a copy of these settings. */
    public VisualizationSettings copy() {
        VisualizationSettings copy = new VisualizationSettings();
        copy.speed = this.speed;
        copy.delay = this.delay;
        copy.highlightColor = this.highlightColor;
        copy.swapColor = this.swapColor;
        copy.sortedColor = this.sortedColor;
        copy.compareColor = this.compareColor;
        copy.pauseOnComparison = this.pauseOnComparison;
        copy.pauseOnSwap = this.pauseOnSwap;
        copy.showStats = this.showStats;
        copy.autoReset = this.autoReset;
        copy.soundEffects = this.soundEffects;
        copy.particleEffects = this.particleEffects;
        copy.displayMode = this.displayMode;
        copy.baseBlock = this.baseBlock;
        copy.minHeight = this.minHeight;
        copy.maxHeight = this.maxHeight;
        return copy;
    }
}
