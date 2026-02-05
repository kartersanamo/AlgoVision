package com.kartersanamo.algoVision.models;

/**
 * Teaching metadata for an algorithm: display name, description, how it works, complexity.
 */
public class AlgorithmInfo {

    private final String category;
    private final String algorithmId;
    private final String displayName;
    private final String description;
    private final String howItWorks;
    private final String timeComplexityBest;
    private final String timeComplexityAvg;
    private final String timeComplexityWorst;
    private final String spaceComplexity;

    public AlgorithmInfo(String category, String algorithmId, String displayName,
                         String description, String howItWorks,
                         String timeComplexityBest, String timeComplexityAvg, String timeComplexityWorst,
                         String spaceComplexity) {
        this.category = category;
        this.algorithmId = algorithmId;
        this.displayName = displayName;
        this.description = description;
        this.howItWorks = howItWorks;
        this.timeComplexityBest = timeComplexityBest;
        this.timeComplexityAvg = timeComplexityAvg;
        this.timeComplexityWorst = timeComplexityWorst;
        this.spaceComplexity = spaceComplexity;
    }

    public String getCategory() { return category; }
    public String getAlgorithmId() { return algorithmId; }
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public String getHowItWorks() { return howItWorks; }
    public String getTimeComplexityBest() { return timeComplexityBest; }
    public String getTimeComplexityAvg() { return timeComplexityAvg; }
    public String getTimeComplexityWorst() { return timeComplexityWorst; }
    public String getSpaceComplexity() { return spaceComplexity; }

    /** Full key for lookup: "category:algorithmId" */
    public String getKey() {
        return category + ":" + algorithmId;
    }
}
