package com.csuft.lostfound.model;

import java.util.ArrayList;
import java.util.List;

public class DashboardOverview {
    private long totalItems;
    private double claimRate;
    private long activeUsers;
    private double avgProcessHours;
    private List<String> trendLabels = new ArrayList<String>();
    private List<Integer> lostTrend = new ArrayList<Integer>();
    private List<Integer> foundTrend = new ArrayList<Integer>();
    private List<String> categoryNames = new ArrayList<String>();
    private List<Integer> categoryValues = new ArrayList<Integer>();
    private List<Integer> retrievalByType = new ArrayList<Integer>();

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public double getClaimRate() {
        return claimRate;
    }

    public void setClaimRate(double claimRate) {
        this.claimRate = claimRate;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public double getAvgProcessHours() {
        return avgProcessHours;
    }

    public void setAvgProcessHours(double avgProcessHours) {
        this.avgProcessHours = avgProcessHours;
    }

    public List<String> getTrendLabels() {
        return trendLabels;
    }

    public void setTrendLabels(List<String> trendLabels) {
        this.trendLabels = trendLabels;
    }

    public List<Integer> getLostTrend() {
        return lostTrend;
    }

    public void setLostTrend(List<Integer> lostTrend) {
        this.lostTrend = lostTrend;
    }

    public List<Integer> getFoundTrend() {
        return foundTrend;
    }

    public void setFoundTrend(List<Integer> foundTrend) {
        this.foundTrend = foundTrend;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public List<Integer> getCategoryValues() {
        return categoryValues;
    }

    public void setCategoryValues(List<Integer> categoryValues) {
        this.categoryValues = categoryValues;
    }

    public List<Integer> getRetrievalByType() {
        return retrievalByType;
    }

    public void setRetrievalByType(List<Integer> retrievalByType) {
        this.retrievalByType = retrievalByType;
    }
}
