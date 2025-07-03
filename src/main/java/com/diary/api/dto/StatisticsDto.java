package com.diary.api.dto;

import java.util.Map;

public class StatisticsDto {
    
    private int totalEntries;
    private int thisMonthEntries;
    private int streak;
    private Map<String, Integer> moodDistribution;
    private double averageWordsPerEntry;
    
    public StatisticsDto() {}
    
    public StatisticsDto(int totalEntries, int thisMonthEntries, int streak, 
                        Map<String, Integer> moodDistribution, double averageWordsPerEntry) {
        this.totalEntries = totalEntries;
        this.thisMonthEntries = thisMonthEntries;
        this.streak = streak;
        this.moodDistribution = moodDistribution;
        this.averageWordsPerEntry = averageWordsPerEntry;
    }
    
    public int getTotalEntries() {
        return totalEntries;
    }
    
    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }
    
    public int getThisMonthEntries() {
        return thisMonthEntries;
    }
    
    public void setThisMonthEntries(int thisMonthEntries) {
        this.thisMonthEntries = thisMonthEntries;
    }
    
    public int getStreak() {
        return streak;
    }
    
    public void setStreak(int streak) {
        this.streak = streak;
    }
    
    public Map<String, Integer> getMoodDistribution() {
        return moodDistribution;
    }
    
    public void setMoodDistribution(Map<String, Integer> moodDistribution) {
        this.moodDistribution = moodDistribution;
    }
    
    public double getAverageWordsPerEntry() {
        return averageWordsPerEntry;
    }
    
    public void setAverageWordsPerEntry(double averageWordsPerEntry) {
        this.averageWordsPerEntry = averageWordsPerEntry;
    }
}