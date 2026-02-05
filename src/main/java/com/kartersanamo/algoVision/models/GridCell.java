package com.kartersanamo.algoVision.models;

/**
 * Grid cell model for pathfinding visualizations.
 */
public class GridCell {

    private final int x;
    private final int z;
    private boolean walkable = true;
    private double cost = 1.0;

    public GridCell(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

