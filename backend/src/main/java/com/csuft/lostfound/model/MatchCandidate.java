package com.csuft.lostfound.model;

import java.util.ArrayList;
import java.util.List;

public class MatchCandidate {
    private Long id;
    private String title;
    private String location;
    private int score;
    private List<String> reasons = new ArrayList<>();

    public MatchCandidate() {
    }

    public MatchCandidate(Long id, String title, String location, int score, List<String> reasons) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.score = score;
        this.reasons = reasons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }
}
