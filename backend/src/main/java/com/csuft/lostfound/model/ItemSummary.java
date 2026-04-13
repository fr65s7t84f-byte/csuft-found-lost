package com.csuft.lostfound.model;

public class ItemSummary {
    private Long id;
    private String title;
    private String location;
    private String lostOrFoundTime;

    public ItemSummary() {
    }

    public ItemSummary(Long id, String title, String location, String lostOrFoundTime) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.lostOrFoundTime = lostOrFoundTime;
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

    public String getLostOrFoundTime() {
        return lostOrFoundTime;
    }

    public void setLostOrFoundTime(String lostOrFoundTime) {
        this.lostOrFoundTime = lostOrFoundTime;
    }
}
