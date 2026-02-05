package com.kartersanamo.algoVision.models;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a 3D region selected by a player for algorithm visualization.
 * Bounded by two corners (pos1 and pos2).
 */
public class Region {

    private final UUID playerId;
    private Location pos1;
    private Location pos2;

    public Region(UUID playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns true if both positions are set and in the same world.
     */
    public boolean isComplete() {
        return pos1 != null && pos2 != null
                && pos1.getWorld() != null && pos2.getWorld() != null
                && pos1.getWorld().equals(pos2.getWorld());
    }

    /** Width along X-axis (block count). */
    public int getWidth() {
        if (!isComplete()) return 0;
        return Math.abs(pos2.getBlockX() - pos1.getBlockX()) + 1;
    }

    /** Height along Y-axis (block count). */
    public int getHeight() {
        if (!isComplete()) return 0;
        return Math.abs(pos2.getBlockY() - pos1.getBlockY()) + 1;
    }

    /** Length along Z-axis (block count). */
    public int getLength() {
        if (!isComplete()) return 0;
        return Math.abs(pos2.getBlockZ() - pos1.getBlockZ()) + 1;
    }

    /** Total block count in the region. */
    public int getVolume() {
        return getWidth() * getHeight() * getLength();
    }

    /** Minimum corner (smallest x, y, z). */
    public Location getMinPoint() {
        if (!isComplete()) return null;
        World w = pos1.getWorld();
        int x = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int y = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int z = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        return new Location(w, x, y, z);
    }

    /** Maximum corner (largest x, y, z). */
    public Location getMaxPoint() {
        if (!isComplete()) return null;
        World w = pos1.getWorld();
        int x = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int y = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int z = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
        return new Location(w, x, y, z);
    }

    /** Returns all block locations within the region (for clearing/rendering). */
    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        if (!isComplete()) return list;
        Location min = getMinPoint();
        Location max = getMaxPoint();
        World w = min.getWorld();
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    list.add(new Location(w, x, y, z));
                }
            }
        }
        return list;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1 != null ? pos1.clone() : null;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2 != null ? pos2.clone() : null;
    }
}
