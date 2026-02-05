package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages region selection (pos1/pos2) per player and region operations (expand, contract, shift).
 */
public class RegionManager {

    private final Map<UUID, Region> playerRegions = new HashMap<>();
    private final AlgoVision plugin;

    public RegionManager(AlgoVision plugin) {
        this.plugin = plugin;
    }

    public void setPos1(Player player, Location loc) {
        Region r = getOrCreateRegion(player);
        r.setPos1(loc);
    }

    public void setPos2(Player player, Location loc) {
        Region r = getOrCreateRegion(player);
        r.setPos2(loc);
    }

    public void clearPos1(Player player) {
        Region r = playerRegions.get(player.getUniqueId());
        if (r != null) r.setPos1(null);
    }

    public void clearPos2(Player player) {
        Region r = playerRegions.get(player.getUniqueId());
        if (r != null) r.setPos2(null);
    }

    public void clearRegion(Player player) {
        playerRegions.remove(player.getUniqueId());
    }

    public Region getRegion(Player player) {
        return playerRegions.get(player.getUniqueId());
    }

    private Region getOrCreateRegion(Player player) {
        return playerRegions.computeIfAbsent(player.getUniqueId(), Region::new);
    }

    /**
     * Expand the region by amount in the given direction.
     * Direction: up, down, north, south, east, west, horizontal (x+z), vertical (y), or all.
     */
    public void expandRegion(Player player, int amount, String direction) {
        Region r = getRegion(player);
        if (r == null || !r.isComplete()) return;
        Location min = r.getMinPoint();
        Location max = r.getMaxPoint();
        if (min == null || max == null) return;
        direction = direction == null ? "all" : direction.toLowerCase();

        switch (direction) {
            case "up":
                max.setY(max.getBlockY() + amount);
                break;
            case "down":
                min.setY(min.getBlockY() - amount);
                break;
            case "north":
                min.setZ(min.getBlockZ() - amount);
                break;
            case "south":
                max.setZ(max.getBlockZ() + amount);
                break;
            case "east":
                max.setX(max.getBlockX() + amount);
                break;
            case "west":
                min.setX(min.getBlockX() - amount);
                break;
            case "vertical":
                min.setY(min.getBlockY() - amount);
                max.setY(max.getBlockY() + amount);
                break;
            case "horizontal":
                min.setX(min.getBlockX() - amount);
                max.setX(max.getBlockX() + amount);
                min.setZ(min.getBlockZ() - amount);
                max.setZ(max.getBlockZ() + amount);
                break;
            case "all":
            default:
                min.setX(min.getBlockX() - amount);
                max.setX(max.getBlockX() + amount);
                min.setY(min.getBlockY() - amount);
                max.setY(max.getBlockY() + amount);
                min.setZ(min.getBlockZ() - amount);
                max.setZ(max.getBlockZ() + amount);
                break;
        }
        r.setPos1(min);
        r.setPos2(max);
    }

    /**
     * Contract the region by amount in the given direction.
     */
    public void contractRegion(Player player, int amount, String direction) {
        Region r = getRegion(player);
        if (r == null || !r.isComplete()) return;
        Location min = r.getMinPoint().clone();
        Location max = r.getMaxPoint().clone();
        direction = direction == null ? "all" : direction.toLowerCase();

        switch (direction) {
            case "up":
                max.setY(max.getBlockY() - amount);
                break;
            case "down":
                min.setY(min.getBlockY() + amount);
                break;
            case "north":
                min.setZ(min.getBlockZ() + amount);
                break;
            case "south":
                max.setZ(max.getBlockZ() - amount);
                break;
            case "east":
                max.setX(max.getBlockX() - amount);
                break;
            case "west":
                min.setX(min.getBlockX() + amount);
                break;
            case "vertical":
                min.setY(min.getBlockY() + amount);
                max.setY(max.getBlockY() - amount);
                break;
            case "horizontal":
                min.setX(min.getBlockX() + amount);
                max.setX(max.getBlockX() - amount);
                min.setZ(min.getBlockZ() + amount);
                max.setZ(max.getBlockZ() - amount);
                break;
            case "all":
            default:
                min.setX(min.getBlockX() + amount);
                max.setX(max.getBlockX() - amount);
                min.setY(min.getBlockY() + amount);
                max.setY(max.getBlockY() - amount);
                min.setZ(min.getBlockZ() + amount);
                max.setZ(max.getBlockZ() - amount);
                break;
        }
        r.setPos1(min);
        r.setPos2(max);
    }

    public boolean canContract(Player player, int amount, String direction) {
        Region r = getRegion(player);
        if (r == null || !r.isComplete()) return false;
        Location min = r.getMinPoint().clone();
        Location max = r.getMaxPoint().clone();
        direction = direction == null ? "all" : direction.toLowerCase();

        switch (direction) {
            case "up": max.setY(max.getBlockY() - amount); break;
            case "down": min.setY(min.getBlockY() + amount); break;
            case "north": min.setZ(min.getBlockZ() + amount); break;
            case "south": max.setZ(max.getBlockZ() - amount); break;
            case "east": max.setX(max.getBlockX() - amount); break;
            case "west": min.setX(min.getBlockX() + amount); break;
            case "vertical":
                min.setY(min.getBlockY() + amount);
                max.setY(max.getBlockY() - amount);
                break;
            case "horizontal":
                min.setX(min.getBlockX() + amount);
                max.setX(max.getBlockX() - amount);
                min.setZ(min.getBlockZ() + amount);
                max.setZ(max.getBlockZ() - amount);
                break;
            case "all":
            default:
                min.setX(min.getBlockX() + amount);
                max.setX(max.getBlockX() - amount);
                min.setY(min.getBlockY() + amount);
                max.setY(max.getBlockY() - amount);
                min.setZ(min.getBlockZ() + amount);
                max.setZ(max.getBlockZ() - amount);
                break;
        }
        int w = Math.abs(max.getBlockX() - min.getBlockX()) + 1;
        int h = Math.abs(max.getBlockY() - min.getBlockY()) + 1;
        int len = Math.abs(max.getBlockZ() - min.getBlockZ()) + 1;
        return w >= 1 && h >= 1 && len >= 1 && validateDimensions(w, h, len);
    }

    /** Shift the entire region by amount in direction (no resize). */
    public void shiftRegion(Player player, int amount, String direction) {
        Region r = getRegion(player);
        if (r == null || !r.isComplete()) return;
        Location pos1 = r.getPos1();
        Location pos2 = r.getPos2();
        if (pos1 == null || pos2 == null) return;
        direction = direction.toLowerCase();
        switch (direction) {
            case "up":
                pos1.add(0, amount, 0);
                pos2.add(0, amount, 0);
                break;
            case "down":
                pos1.add(0, -amount, 0);
                pos2.add(0, -amount, 0);
                break;
            case "north":
                pos1.add(0, 0, -amount);
                pos2.add(0, 0, -amount);
                break;
            case "south":
                pos1.add(0, 0, amount);
                pos2.add(0, 0, amount);
                break;
            case "east":
                pos1.add(amount, 0, 0);
                pos2.add(amount, 0, 0);
                break;
            case "west":
                pos1.add(-amount, 0, 0);
                pos2.add(-amount, 0, 0);
                break;
            default:
                return;
        }
        r.setPos1(pos1);
        r.setPos2(pos2);
    }

    /** Validate region against config limits. */
    public boolean validateRegion(Region region) {
        if (region == null || !region.isComplete()) return false;
        return validateDimensions(region.getWidth(), region.getHeight(), region.getLength())
                && region.getVolume() <= plugin.getConfigManager().getRegionMaxVolume();
    }

    private boolean validateDimensions(int width, int height, int length) {
        return width >= plugin.getConfigManager().getRegionMinWidth()
                && width <= plugin.getConfigManager().getRegionMaxWidth()
                && height >= plugin.getConfigManager().getRegionMinHeight()
                && height <= plugin.getConfigManager().getRegionMaxHeight()
                && length >= plugin.getConfigManager().getRegionMinLength()
                && length <= plugin.getConfigManager().getRegionMaxLength();
    }
}
