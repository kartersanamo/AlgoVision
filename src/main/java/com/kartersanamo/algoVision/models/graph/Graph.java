package com.kartersanamo.algoVision.models.graph;

import java.util.*;

/**
 * Simple adjacency-list graph model for visualizations.
 */
public class Graph {

    private final Map<UUID, GraphNode> nodes = new HashMap<>();
    private final Map<UUID, List<GraphEdge>> adjacency = new HashMap<>();

    public void addNode(GraphNode node) {
        nodes.put(node.getId(), node);
        adjacency.computeIfAbsent(node.getId(), k -> new ArrayList<>());
    }

    public void addEdge(GraphEdge edge) {
        adjacency.computeIfAbsent(edge.getFrom(), k -> new ArrayList<>()).add(edge);
    }

    public GraphNode getNode(UUID id) {
        return nodes.get(id);
    }

    public Collection<GraphNode> getNodes() {
        return nodes.values();
    }

    public List<GraphEdge> getEdges(UUID from) {
        return adjacency.getOrDefault(from, List.of());
    }
}

