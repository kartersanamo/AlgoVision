package com.kartersanamo.algoVision.visualizers;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.visualizers.dynamic.DPVisualizer;
import com.kartersanamo.algoVision.visualizers.dynamic.FibonacciVisualizer;
import com.kartersanamo.algoVision.visualizers.graph.BFSVisualizer;
import com.kartersanamo.algoVision.visualizers.graph.DFSVisualizer;
import com.kartersanamo.algoVision.visualizers.graph.DijkstraVisualizer;
import com.kartersanamo.algoVision.visualizers.graph.GraphVisualizer;
import com.kartersanamo.algoVision.visualizers.pathfinding.AStarVisualizer;
import com.kartersanamo.algoVision.visualizers.pathfinding.PathfindingVisualizer;
import com.kartersanamo.algoVision.visualizers.searching.BinarySearchVisualizer;
import com.kartersanamo.algoVision.visualizers.searching.JumpSearchVisualizer;
import com.kartersanamo.algoVision.visualizers.searching.LinearSearchVisualizer;
import com.kartersanamo.algoVision.visualizers.searching.SearchingVisualizer;
import com.kartersanamo.algoVision.visualizers.sorting.*;
import com.kartersanamo.algoVision.visualizers.tree.BSTVisualizer;
import com.kartersanamo.algoVision.visualizers.tree.TreeVisualizer;

/**
 * Factory for creating visualizers by category and algorithm.
 */
public class VisualizerFactory {

    private final AlgoVision plugin;

    public VisualizerFactory(AlgoVision plugin) {
        this.plugin = plugin;
    }

    public Visualizer createVisualizer(String category, String algorithm, VisualizationSession session) {
        category = category.toLowerCase();
        algorithm = algorithm.toLowerCase();

        return switch (category) {
            case "sorting" -> createSortingVisualizer(algorithm, session);
            case "searching" -> createSearchingVisualizer(algorithm, session);
            case "graph" -> createGraphVisualizer(algorithm, session);
            case "tree" -> createTreeVisualizer(algorithm, session);
            case "pathfinding" -> createPathfindingVisualizer(algorithm, session);
            case "dynamic" -> createDynamicVisualizer(algorithm, session);
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }

    private SortingVisualizer createSortingVisualizer(String algorithm, VisualizationSession session) {
        return switch (algorithm) {
            case "bubble" -> new BubbleSortVisualizer(plugin, session);
            case "quick" -> new QuickSortVisualizer(plugin, session);
            case "merge" -> new MergeSortVisualizer(plugin, session);
            case "insertion" -> new InsertionSortVisualizer(plugin, session);
            case "selection" -> new SelectionSortVisualizer(plugin, session);
            case "heap" -> new HeapSortVisualizer(plugin, session);
            case "shell" -> new ShellSortVisualizer(plugin, session);
            default -> throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        };
    }

    private SearchingVisualizer createSearchingVisualizer(String algorithm, VisualizationSession session) {
        return switch (algorithm) {
            case "binary" -> new BinarySearchVisualizer(plugin, session);
            case "linear" -> new LinearSearchVisualizer(plugin, session);
            case "jump" -> new JumpSearchVisualizer(plugin, session);
            default -> throw new IllegalArgumentException("Unknown searching algorithm: " + algorithm);
        };
    }

    private GraphVisualizer createGraphVisualizer(String algorithm, VisualizationSession session) {
        return switch (algorithm) {
            case "bfs" -> new BFSVisualizer(plugin, session);
            case "dfs" -> new DFSVisualizer(plugin, session);
            case "dijkstra" -> new DijkstraVisualizer(plugin, session);
            default -> throw new IllegalArgumentException("Unknown graph algorithm: " + algorithm);
        };
    }

    private TreeVisualizer createTreeVisualizer(String algorithm, VisualizationSession session) {
        if (algorithm.equals("bst")) {
            return new BSTVisualizer(plugin, session);
        }
        throw new IllegalArgumentException("Unknown tree algorithm: " + algorithm);
    }

    private PathfindingVisualizer createPathfindingVisualizer(String algorithm, VisualizationSession session) {
        if (algorithm.equals("astar")) {
            return new AStarVisualizer(plugin, session);
        }
        throw new IllegalArgumentException("Unknown pathfinding algorithm: " + algorithm);
    }

    private DPVisualizer createDynamicVisualizer(String algorithm, VisualizationSession session) {
        if (algorithm.equals("fibonacci")) {
            return new FibonacciVisualizer(plugin, session);
        }
        throw new IllegalArgumentException("Unknown dynamic programming algorithm: " + algorithm);
    }
}

