package com.csuft.lostfound.model;

import java.util.ArrayList;
import java.util.List;

public class CampusInsights {
    private CampusMapInfo map;
    private List<LeaderboardEntry> leaderboard = new ArrayList<>();
    private List<HotspotPoint> hotspots = new ArrayList<>();

    public CampusMapInfo getMap() {
        return map;
    }

    public void setMap(CampusMapInfo map) {
        this.map = map;
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<HotspotPoint> getHotspots() {
        return hotspots;
    }

    public void setHotspots(List<HotspotPoint> hotspots) {
        this.hotspots = hotspots;
    }
}
