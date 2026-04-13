package com.csuft.lostfound.model;

public class LeaderboardEntry {
    private int rank;
    private String nickname;
    private String college;
    private int creditScore;
    private int publishedCount;
    private int returnedCount;
    private int keepDays;

    public LeaderboardEntry() {
    }

    public LeaderboardEntry(int rank, String nickname, String college, int creditScore, int publishedCount, int returnedCount, int keepDays) {
        this.rank = rank;
        this.nickname = nickname;
        this.college = college;
        this.creditScore = creditScore;
        this.publishedCount = publishedCount;
        this.returnedCount = returnedCount;
        this.keepDays = keepDays;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getPublishedCount() {
        return publishedCount;
    }

    public void setPublishedCount(int publishedCount) {
        this.publishedCount = publishedCount;
    }

    public int getReturnedCount() {
        return returnedCount;
    }

    public void setReturnedCount(int returnedCount) {
        this.returnedCount = returnedCount;
    }

    public int getKeepDays() {
        return keepDays;
    }

    public void setKeepDays(int keepDays) {
        this.keepDays = keepDays;
    }
}
