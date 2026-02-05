package com.kartersanamo.algoVision.utils;

import com.kartersanamo.algoVision.models.AlgorithmInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registry of algorithm metadata for teaching: descriptions, how it works, complexity.
 */
public final class AlgorithmRegistry {

    private static final Map<String, AlgorithmInfo> BY_KEY = new HashMap<>();

    static {
        // ─── SORTING ───
        register(new AlgorithmInfo("sorting", "bubble",
                "Bubble Sort",
                "Repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order. Passes are repeated until the list is sorted.",
                "Each pass moves the largest unsorted value to its final position (like a bubble rising). Compare adjacent pairs left-to-right; swap if left > right. After n passes, the list is sorted.",
                "O(n)", "O(n²)", "O(n²)",
                "O(1)"));

        register(new AlgorithmInfo("sorting", "quick",
                "Quick Sort",
                "Divides the array by choosing a pivot, partitioning so smaller elements are left and larger right, then recursively sorts the two parts.",
                "Pick a pivot (e.g. middle). Partition: move elements < pivot left, > pivot right. Recursively quicksort the left and right sub-arrays. Combine (pivot is already in place).",
                "O(n log n)", "O(n log n)", "O(n²)",
                "O(log n)"));

        register(new AlgorithmInfo("sorting", "merge",
                "Merge Sort",
                "Divides the array in half recursively until sub-arrays have one element, then merges pairs of sorted sub-arrays into larger sorted arrays.",
                "Split the array in half until each half has 0–1 elements. Merge: take the smaller front element from two sorted halves and add to result. Repeat until one half is empty, then append the rest.",
                "O(n log n)", "O(n log n)", "O(n log n)",
                "O(n)"));

        register(new AlgorithmInfo("sorting", "insertion",
                "Insertion Sort",
                "Builds the final sorted array one item at a time by repeatedly taking the next element and inserting it into its correct position among the already-sorted elements.",
                "Start with the first element (sorted). For each new element, shift larger sorted elements one position right and insert the new element into the gap. Like sorting a hand of cards.",
                "O(n)", "O(n²)", "O(n²)",
                "O(1)"));

        register(new AlgorithmInfo("sorting", "selection",
                "Selection Sort",
                "Repeatedly finds the minimum from the unsorted part and swaps it with the first unsorted element, growing the sorted region from the left.",
                "For each position i, find the minimum in the range [i, n-1] and swap it with the element at i. After n-1 passes, the array is sorted.",
                "O(n²)", "O(n²)", "O(n²)",
                "O(1)"));

        register(new AlgorithmInfo("sorting", "heap",
                "Heap Sort",
                "Uses a binary heap to repeatedly extract the maximum (or minimum) and place it at the end, building the sorted array from the back.",
                "Build a max-heap from the array. Repeatedly swap the root (max) with the last element, reduce heap size, and heapify the new root. The array is sorted when the heap is empty.",
                "O(n log n)", "O(n log n)", "O(n log n)",
                "O(1)"));

        register(new AlgorithmInfo("sorting", "shell",
                "Shell Sort",
                "Generalization of insertion sort that sorts elements far apart first, then reduces the gap to make the array nearly sorted, then does a final insertion sort.",
                "Use a decreasing sequence of gaps (e.g. n/2, n/4, ..., 1). For each gap, do an insertion sort on elements that are gap positions apart. Final pass with gap 1 finishes the sort.",
                "O(n log n)", "depends on gap", "O(n²)",
                "O(1)"));

        // ─── SEARCHING ───
        register(new AlgorithmInfo("searching", "binary",
                "Binary Search",
                "Finds the position of a target value in a sorted array by repeatedly comparing with the middle element and discarding half of the search space.",
                "Compare the target to the middle element. If equal, done. If target < middle, search the left half; if target > middle, search the right half. Repeat until found or the range is empty. Requires a sorted array.",
                "O(1)", "O(log n)", "O(log n)",
                "O(1)"));

        register(new AlgorithmInfo("searching", "linear",
                "Linear Search",
                "Scans the array from start to end, comparing each element with the target until found or the end is reached.",
                "Start at index 0. Compare each element with the target. If equal, return the index. If the end is reached without finding, the target is not in the array. Works on any array (sorted or not).",
                "O(1)", "O(n)", "O(n)",
                "O(1)"));

        register(new AlgorithmInfo("searching", "jump",
                "Jump Search",
                "Jumps ahead by a fixed step (e.g. √n) in a sorted array, then performs a linear search backward in the block where the target might be.",
                "In a sorted array, jump by step size √n. When you land on a value ≥ target (or pass the end), do a linear search backward in the previous block. Fewer comparisons than linear search when sorted.",
                "O(√n)", "O(√n)", "O(n)",
                "O(1)"));

        // ─── GRAPH ───
        register(new AlgorithmInfo("graph", "bfs",
                "Breadth-First Search (BFS)",
                "Explores a graph level by level: visit all neighbors of the current level before moving to the next level. Uses a queue.",
                "Start at a node, enqueue it. While the queue is not empty, dequeue a node, visit it, and enqueue all unvisited neighbors. Marks nodes as visited to avoid cycles. Finds shortest path in unweighted graphs.",
                "O(V+E)", "O(V+E)", "O(V+E)",
                "O(V)"));

        register(new AlgorithmInfo("graph", "dfs",
                "Depth-First Search (DFS)",
                "Explores as far as possible along each branch before backtracking. Uses a stack (or recursion).",
                "Start at a node, mark visited. For each unvisited neighbor, recursively run DFS. When a dead end is reached, backtrack. Can be implemented with an explicit stack instead of recursion.",
                "O(V+E)", "O(V+E)", "O(V+E)",
                "O(V)"));

        register(new AlgorithmInfo("graph", "dijkstra",
                "Dijkstra's Algorithm",
                "Finds shortest paths from a source to all other nodes in a graph with non-negative edge weights. Uses a priority queue.",
                "Initialize distances: source=0, others=∞. Repeatedly take the unvisited node with smallest distance, relax all its edges (update neighbor distances if a shorter path is found), and mark it visited. Stops when all are visited or target is reached.",
                "O((V+E) log V)", "O((V+E) log V)", "O((V+E) log V)",
                "O(V)"));

        // ─── TREE ───
        register(new AlgorithmInfo("tree", "bst",
                "Binary Search Tree (BST)",
                "A tree where each node has at most two children and left < node < right. Traversals (e.g. level-order) visit nodes in a defined order.",
                "In a BST, for every node: left subtree has smaller keys, right subtree has larger keys. Level-order (BFS) visits nodes by depth. Used for efficient search, insert, delete when balanced.",
                "O(log n)", "O(log n)", "O(n)",
                "O(n)"));

        // ─── PATHFINDING ───
        register(new AlgorithmInfo("pathfinding", "astar",
                "A* Pathfinding",
                "Finds the shortest path between two points using a heuristic to guide the search. Combines cost-so-far (g) and estimated cost-to-goal (h).",
                "Maintain an open set of nodes to explore. For each node, f = g + h (g = cost from start, h = heuristic to goal). Expand the node with lowest f, update neighbors. When the goal is reached, reconstruct the path. Optimal if heuristic is admissible.",
                "O(b^d)", "O(b^d)", "O(b^d)",
                "O(b^d)"));

        // ─── DYNAMIC ───
        register(new AlgorithmInfo("dynamic", "fibonacci",
                "Fibonacci (DP)",
                "Computes the nth Fibonacci number using dynamic programming: store previously computed values to avoid redundant recursion.",
                "F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2). DP: fill a table from 0 to n, each entry = sum of previous two. No recursion, each subproblem solved once. Classic example of memoization / tabulation.",
                "O(n)", "O(n)", "O(n)",
                "O(n)"));
    }

    private static void register(AlgorithmInfo info) {
        BY_KEY.put(info.getKey(), info);
    }

    public static Optional<AlgorithmInfo> get(String category, String algorithmId) {
        if (category == null || algorithmId == null) return Optional.empty();
        return Optional.ofNullable(BY_KEY.get(category.toLowerCase() + ":" + algorithmId.toLowerCase()));
    }

    public static Optional<AlgorithmInfo> getByKey(String key) {
        if (key == null) return Optional.empty();
        return Optional.ofNullable(BY_KEY.get(key.toLowerCase()));
    }

    public static Map<String, AlgorithmInfo> getAll() {
        return new HashMap<>(BY_KEY);
    }

    private AlgorithmRegistry() {}
}
