package com.kartersanamo.algoVision.models.graph;

import org.bukkit.Location;

import java.util.UUID;

/**
 * Node in a graph, with optional world location backing.
 */
public class GraphNode {

    private final UUID id;
    private final Location location;

    public GraphNode(UUID id, Location location) {
        this.id = id;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }
}

