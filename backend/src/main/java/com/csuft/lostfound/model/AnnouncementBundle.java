package com.csuft.lostfound.model;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementBundle {
    private List<ItemSummary> todayLost = new ArrayList<ItemSummary>();
    private List<ItemSummary> todayFound = new ArrayList<ItemSummary>();
    private List<SimpleNotice> urgent = new ArrayList<SimpleNotice>();
    private List<SimpleNotice> important = new ArrayList<SimpleNotice>();

    public List<ItemSummary> getTodayLost() {
        return todayLost;
    }

    public void setTodayLost(List<ItemSummary> todayLost) {
        this.todayLost = todayLost;
    }

    public List<ItemSummary> getTodayFound() {
        return todayFound;
    }

    public void setTodayFound(List<ItemSummary> todayFound) {
        this.todayFound = todayFound;
    }

    public List<SimpleNotice> getUrgent() {
        return urgent;
    }

    public void setUrgent(List<SimpleNotice> urgent) {
        this.urgent = urgent;
    }

    public List<SimpleNotice> getImportant() {
        return important;
    }

    public void setImportant(List<SimpleNotice> important) {
        this.important = important;
    }
}
