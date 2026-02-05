package com.kartersanamo.algoVision.visualizers;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.AlgorithmInfo;
import com.kartersanamo.algoVision.models.AlgorithmStats;
import com.kartersanamo.algoVision.models.Region;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.models.VisualizationSettings;
import com.kartersanamo.algoVision.utils.AlgorithmRegistry;
import com.kartersanamo.algoVision.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

/**
 * Base class for all visualizations. Handles scheduling and common utilities.
 */
public abstract class Visualizer extends BukkitRunnable {

    protected final AlgoVision plugin;
    protected final VisualizationSession session;
    protected final Region region;
    protected final VisualizationSettings settings;
    protected final AlgorithmStats stats;

    protected int[] data;
    protected int currentStep = 0;
    protected VisualizerState state = VisualizerState.IDLE;

    protected Visualizer(AlgoVision plugin, VisualizationSession session) {
        this.plugin = plugin;
        this.session = session;
        this.region = session.getRegion();
        this.settings = session.getSettings();
        this.stats = session.getStats();
    }

    // Abstract methods that each visualizer must implement
    protected abstract void initialize();
    protected abstract void executeStep();
    protected abstract boolean isComplete();
    protected abstract void onComplete();

    // Common methods
    public void start() {
        initialize();
        if (settings.isShowStats()) {
            Player p = Bukkit.getPlayer(session.getPlayerId());
            AlgorithmInfo info = getAlgorithmInfo();
            if (p != null && info != null) {
                MessageUtils.sendAlgorithmIntro(p, info);
            }
        }
        state = VisualizerState.RUNNING;
        this.runTaskTimer(plugin, settings.getDelay(), settings.getSpeed());
    }

    /** Look up teaching metadata for this session's algorithm. */
    protected AlgorithmInfo getAlgorithmInfo() {
        return AlgorithmRegistry.getByKey(session.getAlgorithmName()).orElse(null);
    }

    /** Send completion summary (teaching or compact) to the player. */
    protected void sendCompletionDisplay(Player player, AlgorithmInfo info) {
        if (player == null) return;
        stats.recordSteps(currentStep);
        if (settings.isShowStats() && info != null) {
            MessageUtils.sendAlgorithmComplete(player, info, stats, currentStep);
        } else {
            MessageUtils.sendCompactComplete(player, session.getAlgorithmName(), stats);
        }
    }

    @Override
    public void run() {
        if (state != VisualizerState.RUNNING) {
            return;
        }

        if (isComplete()) {
            stats.recordSteps(currentStep);
            onComplete();
            state = VisualizerState.COMPLETED;
            this.cancel();
            return;
        }

        executeStep();
        currentStep++;
    }

    public void pause() {
        state = VisualizerState.PAUSED;
    }

    public void resume() {
        state = VisualizerState.RUNNING;
    }

    public void stop() {
        state = VisualizerState.STOPPED;
        cleanup();
        this.cancel();
    }

    /**
     * Advance a number of steps while paused.
     */
    public void step(int steps) {
        if (steps <= 0) return;
        for (int i = 0; i < steps && !isComplete(); i++) {
            executeStep();
            currentStep++;
        }
    }

    /**
     * Adjust speed multiplicatively (lower = faster ticks).
     */
    public void setSpeed(double multiplier) {
        if (multiplier <= 0) return;
        int newPeriod = (int) Math.max(1, settings.getSpeed() * (1.0 / multiplier));
        settings.setSpeed(newPeriod);
        this.cancel();
        this.runTaskTimer(plugin, 0L, newPeriod);
    }

    // Utility methods for all visualizers
    protected void setBlock(Location loc, Material material) {
        if (loc == null || material == null) return;
        loc.getBlock().setType(material);
    }

    protected void setPillarHeight(int index, int height) {
        if (region == null) return;
        Location base = getBaseLocation(index);
        for (int y = 0; y < height; y++) {
            setBlock(base.clone().add(0, y, 0), settings.getBaseBlock());
        }
    }

    protected void highlightBlock(Location loc, Material highlightMaterial) {
        if (loc == null) return;
        setBlock(loc, highlightMaterial);
        if (settings.isParticleEffects()) {
            Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.FLAME, loc.clone().add(0.5, 1, 0.5), 5);
        }
        if (settings.isSoundEffects()) {
            Objects.requireNonNull(loc.getWorld()).playSound(loc, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 1.0f);
        }
    }

    protected Location getBaseLocation(int index) {
        Location min = region.getMinPoint();
        return min.clone().add(index, 0, 0);
    }

    protected void cleanup() {
        if (settings.isAutoReset()) {
            clearRegion();
        }
    }

    protected void clearRegion() {
        if (region == null) return;
        for (Location loc : region.getAllLocations()) {
            loc.getBlock().setType(Material.AIR);
        }
    }
}
