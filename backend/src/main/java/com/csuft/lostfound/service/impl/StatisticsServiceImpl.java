package com.csuft.lostfound.service.impl;

import com.csuft.lostfound.model.CampusInsights;
import com.csuft.lostfound.model.CampusMapInfo;
import com.csuft.lostfound.model.HotspotPoint;
import com.csuft.lostfound.model.LeaderboardEntry;
import com.csuft.lostfound.repository.HotspotRepository;
import com.csuft.lostfound.repository.UserRepository;
import com.csuft.lostfound.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final UserRepository userRepository;
    private final HotspotRepository hotspotRepository;

    public StatisticsServiceImpl(UserRepository userRepository, HotspotRepository hotspotRepository) {
        this.userRepository = userRepository;
        this.hotspotRepository = hotspotRepository;
    }

    @Override
    public List<LeaderboardEntry> getLeaderboard() {
        return userRepository.findAll().stream()
                .filter(user -> "user".equalsIgnoreCase(user.getRole()))
                .sorted(Comparator.comparingInt(user -> -user.getCreditScore()))
                .limit(8)
                .map(user -> new LeaderboardEntry(
                        0,
                        user.getNickname(),
                        "综合学院",
                        user.getCreditScore(),
                        Math.max(user.getCreditScore() / 8, 5),
                        Math.max(user.getCreditScore() / 12, 3),
                        Math.max(user.getCreditScore() / 10, 7)
                ))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    for (int index = 0; index < list.size(); index++) {
                        list.get(index).setRank(index + 1);
                    }
                    return list;
                }));
    }

    @Override
    public List<HotspotPoint> getHeatmap() {
        return hotspotRepository.findAll().stream()
                .map(item -> new HotspotPoint(item.getId(), item.getName(), item.getPointType(), item.getX(), item.getY(), item.getWeight(), item.getPickups(), item.getPeakTime()))
                .collect(Collectors.toList());
    }

    @Override
    public CampusInsights getCampusInsights() {
        CampusMapInfo mapInfo = new CampusMapInfo();
        mapInfo.setSchoolName("中南林业科技大学");
        mapInfo.setImageUrl("/maps/csuft-campus-latest.jpg");
        mapInfo.setSourceName("本地静态图片");
        mapInfo.setSourceUrl("/maps/csuft-campus-latest.jpg");
        mapInfo.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        CampusInsights insights = new CampusInsights();
        insights.setMap(mapInfo);
        insights.setLeaderboard(getLeaderboard());
        insights.setHotspots(getHeatmap());
        return insights;
    }
}
