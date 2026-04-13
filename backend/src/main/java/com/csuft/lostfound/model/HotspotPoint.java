package com.csuft.lostfound.model;

public class HotspotPoint {
    private long id;
    private String name;
    private String type;
    private int x;
    private int y;
    private int weight;
    private int pickups;
    private String peakTime;

    public HotspotPoint() {
    }

    public HotspotPoint(long id, String name, String type, int x, int y, int weight, int pickups, String peakTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.pickups = pickups;
        this.peakTime = peakTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPickups() {
        return pickups;
    }

    public void setPickups(int pickups) {
        this.pickups = pickups;
    }

    public String getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(String peakTime) {
        this.peakTime = peakTime;
    }
}
