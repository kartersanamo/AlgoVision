package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.AlgoVision;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Generates and stores per-player input data arrays for visualizations.
 */
public class DataManager {

    private final AlgoVision plugin;
    private final Map<UUID, int[]> playerData = new HashMap<>();
    private final Random random = new Random();

    public DataManager(AlgoVision plugin) {
        this.plugin = plugin;
    }

    public int[] generateData(String pattern, int size) {
        if (pattern == null) pattern = "random";
        pattern = pattern.toLowerCase();
        return switch (pattern) {
            case "sorted" -> generateSorted(size);
            case "reversed" -> generateReversed(size);
            case "nearlysorted" -> generateNearlySorted(size);
            case "mountain" -> generateMountain(size);
            case "valley" -> generateValley(size);
            default -> generateRandom(size);
        };
    }

    public int[] generateRandom(int size) {
        size = clampSize(size);
        int min = plugin.getConfigManager().getDataRandomMin();
        int max = plugin.getConfigManager().getDataRandomMax();
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(max - min + 1) + min;
        }
        return data;
    }

    public int[] generateSorted(int size) {
        size = clampSize(size);
        int[] data = generateRandom(size);
        java.util.Arrays.sort(data);
        return data;
    }

    public int[] generateReversed(int size) {
        int[] sorted = generateSorted(size);
        for (int i = 0, j = sorted.length - 1; i < j; i++, j--) {
            int tmp = sorted[i];
            sorted[i] = sorted[j];
            sorted[j] = tmp;
        }
        return sorted;
    }

    public int[] generateNearlySorted(int size) {
        int[] sorted = generateSorted(size);
        int swaps = Math.max(1, sorted.length / 10);
        for (int k = 0; k < swaps; k++) {
            int i = random.nextInt(sorted.length);
            int j = random.nextInt(sorted.length);
            int tmp = sorted[i];
            sorted[i] = sorted[j];
            sorted[j] = tmp;
        }
        return sorted;
    }

    public int[] generateMountain(int size) {
        size = clampSize(size);
        int mid = size / 2;
        int[] data = new int[size];
        int val = 1;
        for (int i = 0; i <= mid; i++) {
            data[i] = val++;
        }
        for (int i = mid + 1; i < size; i++) {
            data[i] = --val;
        }
        return data;
    }

    public int[] generateValley(int size) {
        int[] mountain = generateMountain(size);
        for (int i = 0; i < mountain.length; i++) {
            mountain[i] = plugin.getConfigManager().getDataRandomMax() - mountain[i];
        }
        return mountain;
    }

    public void setCustomData(UUID playerId, int[] data) {
        playerData.put(playerId, data);
    }

    public int[] getData(UUID playerId) {
        return playerData.get(playerId);
    }

    public void clearData(UUID playerId) {
        playerData.remove(playerId);
    }

    private int clampSize(int size) {
        int max = plugin.getConfigManager().getDataMaxSize();
        int def = plugin.getConfigManager().getDataDefaultSize();
        if (size <= 0) size = def;
        return Math.min(size, max);
    }
}
