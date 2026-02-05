package com.kartersanamo.algoVision.visualizers.tree;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.models.VisualizationSession;
import com.kartersanamo.algoVision.models.tree.BinaryTreeNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Simple BST build + level-order traversal visualization.
 */
public class BSTVisualizer extends TreeVisualizer {

    private BinaryTreeNode root;
    private final Queue<BinaryTreeNode> queue = new LinkedList<>();
    private final Random random = new Random();

    public BSTVisualizer(AlgoVision plugin, VisualizationSession session) {
        super(plugin, session);
    }

    @Override
    protected void initialize() {
        // Build a random BST from data length
        int count = Math.max(1, region.getWidth());
        for (int i = 0; i < count; i++) {
            insert(random.nextInt(100));
        }
        if (root != null) queue.add(root);
    }

    private void insert(int value) {
        if (root == null) {
            root = new BinaryTreeNode(value);
            return;
        }
        BinaryTreeNode current = root;
        while (true) {
            if (value < current.getValue()) {
                if (current.getLeft() == null) {
                    current.setLeft(new BinaryTreeNode(value));
                    return;
                }
                current = (BinaryTreeNode) current.getLeft();
            } else {
                if (current.getRight() == null) {
                    current.setRight(new BinaryTreeNode(value));
                    return;
                }
                current = (BinaryTreeNode) current.getRight();
            }
        }
    }

    @Override
    protected void executeStep() {
        if (queue.isEmpty()) return;
        BinaryTreeNode node = queue.poll();
        stats.incrementArrayAccesses();
        highlightTreePosition(node.getValue() % region.getWidth());
        if (node.getLeft() != null) queue.add((BinaryTreeNode) node.getLeft());
        if (node.getRight() != null) queue.add((BinaryTreeNode) node.getRight());
    }

    @Override
    protected boolean isComplete() {
        return queue.isEmpty();
    }

    @Override
    protected void onComplete() {
        stats.recordDuration(System.currentTimeMillis() - session.getStartTime());
        Player player = Bukkit.getPlayer(session.getPlayerId());
        sendCompletionDisplay(player, getAlgorithmInfo());
        plugin.getStatsManager().recordStats(session.getAlgorithmName(), stats);
    }
}

