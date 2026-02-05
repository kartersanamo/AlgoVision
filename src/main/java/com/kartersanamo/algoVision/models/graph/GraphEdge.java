package com.kartersanamo.algoVision.models.graph;

import java.util.UUID;

/**
 * Directed edge between two graph nodes with a weight.
 */
public class GraphEdge {

    private final UUID from;
    private final UUID to;
    private final double weight;

    public GraphEdge(UUID from, UUID to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }
}

