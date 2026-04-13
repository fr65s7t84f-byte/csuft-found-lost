package com.csuft.lostfound.service;

import com.csuft.lostfound.model.CampusInsights;
import com.csuft.lostfound.model.HotspotPoint;
import com.csuft.lostfound.model.LeaderboardEntry;

import java.util.List;

public interface StatisticsService {
    List<LeaderboardEntry> getLeaderboard();

    List<HotspotPoint> getHeatmap();

    CampusInsights getCampusInsights();
}
