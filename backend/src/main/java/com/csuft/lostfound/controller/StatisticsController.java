package com.csuft.lostfound.controller;

import com.csuft.lostfound.common.ApiResponse;
import com.csuft.lostfound.model.CampusInsights;
import com.csuft.lostfound.model.HotspotPoint;
import com.csuft.lostfound.model.LeaderboardEntry;
import com.csuft.lostfound.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/leaderboard")
    public ApiResponse<List<LeaderboardEntry>> leaderboard() {
        return ApiResponse.success(statisticsService.getLeaderboard());
    }

    @GetMapping("/heatmap")
    public ApiResponse<List<HotspotPoint>> heatmap() {
        return ApiResponse.success(statisticsService.getHeatmap());
    }

    @GetMapping("/campus-insights")
    public ApiResponse<CampusInsights> campusInsights() {
        return ApiResponse.success(statisticsService.getCampusInsights());
    }
}
