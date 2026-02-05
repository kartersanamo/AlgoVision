package com.kartersanamo.algoVision.utils;

import com.kartersanamo.algoVision.models.AlgorithmInfo;
import com.kartersanamo.algoVision.models.AlgorithmStats;
import org.bukkit.entity.Player;

/**
 * Formatted messages and teaching displays: headers, bars, algorithm intros and completion summaries.
 */
public final class MessageUtils {

    private static final String H_LINE = "§8§m                    §r §6✦ §eAlgoVision §6✦ §8§m                    ";
    private static final String H_LINE_SHORT = "§8§m────────────────────────────────────────";
    private static final int BAR_LENGTH = 12;

    private MessageUtils() {}

    /** Send a horizontal rule. */
    public static void sendLine(Player player) {
        if (player != null) player.sendMessage(H_LINE_SHORT);
    }

    /** Send a full-width header line. */
    public static void sendHeaderLine(Player player) {
        if (player != null) player.sendMessage(H_LINE);
    }

    /** Send a centered-style title (e.g. "▶ BUBBLE SORT ◀"). */
    public static void sendTitle(Player player, String title) {
        if (player == null || title == null) return;
        player.sendMessage("");
        player.sendMessage("  §6§l▶ §e§l" + title.toUpperCase() + " §6§l◀");
        player.sendMessage("");
    }

    /** Send a labeled line: "  Label: value" with consistent formatting. */
    public static void sendLabel(Player player, String label, String value) {
        if (player == null) return;
        player.sendMessage("  §7" + label + " §f" + (value != null ? value : ""));
    }

    /** Build a simple text bar (e.g. ████████░░░░) for 0.0–1.0. */
    public static String bar(double fraction) {
        if (fraction <= 0) return "§8" + "░".repeat(BAR_LENGTH);
        if (fraction >= 1) return "§a" + "█".repeat(BAR_LENGTH);
        int filled = (int) Math.round(fraction * BAR_LENGTH);
        return "§a" + "█".repeat(filled) + "§8" + "░".repeat(BAR_LENGTH - filled);
    }

    /** Send a bar with label and optional numeric value. */
    public static void sendBar(Player player, String label, double fraction, String valueText) {
        if (player == null) return;
        String v = valueText != null && !valueText.isEmpty() ? " §f" + valueText : "";
        player.sendMessage("  §7" + label + " " + bar(fraction) + v);
    }

    /** Send the algorithm intro block (when a visualization starts). */
    public static void sendAlgorithmIntro(Player player, AlgorithmInfo info) {
        if (player == null || info == null) return;
        sendHeaderLine(player);
        sendTitle(player, info.getDisplayName());
        sendLabel(player, "What it does:", "");
        player.sendMessage("  §f" + info.getDescription());
        player.sendMessage("");
        sendLabel(player, "How it works:", "");
        player.sendMessage("  §f" + info.getHowItWorks());
        player.sendMessage("");
        player.sendMessage("  §6Time complexity §7(Best / Avg / Worst): §f"
                + info.getTimeComplexityBest() + " §7/ §f"
                + info.getTimeComplexityAvg() + " §7/ §f"
                + info.getTimeComplexityWorst());
        sendLabel(player, "Space complexity:", info.getSpaceComplexity());
        sendHeaderLine(player);
    }

    /** Send the full explain block (for /algo explain). Same as intro but with extra header. */
    public static void sendAlgorithmExplain(Player player, AlgorithmInfo info) {
        if (player == null || info == null) return;
        player.sendMessage("§e§l📚 Algorithm: §6" + info.getDisplayName());
        sendAlgorithmIntro(player, info);
    }

    /**
     * Send the completion summary: real time, steps, comparisons/swaps (if any), and theoretical complexity.
     */
    public static void sendAlgorithmComplete(Player player, AlgorithmInfo info,
                                             AlgorithmStats stats, int totalSteps) {
        if (player == null) return;
        sendHeaderLine(player);
        player.sendMessage("  §a§l✓ COMPLETE §7— §f" + (info != null ? info.getDisplayName() : "Algorithm"));
        player.sendMessage("");
        long ms = stats.getDurationMs();
        double sec = ms / 1000.0;
        String timeStr = ms < 1000 ? ms + " ms" : String.format("%.2f s", sec);
        sendLabel(player, "Real time:", "§f" + timeStr);
        sendLabel(player, "Steps:", "§f" + totalSteps);
        if (stats.getComparisons() > 0) {
            sendLabel(player, "Comparisons:", "§f" + stats.getComparisons());
        }
        if (stats.getSwaps() > 0) {
            sendLabel(player, "Swaps:", "§f" + stats.getSwaps());
        }
        if (stats.getArrayAccesses() > 0) {
            sendLabel(player, "Array accesses:", "§f" + stats.getArrayAccesses());
        }
        player.sendMessage("");
        if (info != null) {
            player.sendMessage("  §6Theoretical time complexity §7(Best / Avg / Worst):");
            player.sendMessage("  §f  " + info.getTimeComplexityBest() + " §7/ §f"
                    + info.getTimeComplexityAvg() + " §7/ §f" + info.getTimeComplexityWorst());
        }
        sendHeaderLine(player);
    }

    /** Send a compact one-line completion (legacy style) if teaching display is disabled. */
    public static void sendCompactComplete(Player player, String algorithmName, AlgorithmStats stats) {
        if (player == null) return;
        player.sendMessage("§a§l" + algorithmName + " Complete! §7| §eComparisons: §f" + stats.getComparisons()
                + " §7| §eSwaps: §f" + stats.getSwaps() + " §7| §eTime: §f" + stats.getDurationMs() + " ms");
    }
}
