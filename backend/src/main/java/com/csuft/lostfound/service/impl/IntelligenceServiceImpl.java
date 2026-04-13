package com.csuft.lostfound.service.impl;

import com.csuft.lostfound.dto.ImageAnalyzeRequest;
import com.csuft.lostfound.dto.MatchPreviewRequest;
import com.csuft.lostfound.model.ImageAnalyzeResult;
import com.csuft.lostfound.model.MatchCandidate;
import com.csuft.lostfound.service.IntelligenceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class IntelligenceServiceImpl implements IntelligenceService {
    private static final List<MockItem> FOUND_ITEMS = Arrays.asList(
            new MockItem(2001L, "黑色帆布书包", "箱包", "图书馆", Arrays.asList("黑色", "帆布", "书包", "拉链")),
            new MockItem(2002L, "银色保温杯", "生活用品", "求是楼", Arrays.asList("银色", "保温杯", "金属")),
            new MockItem(2003L, "白色耳机盒", "电子配件", "第一食堂", Arrays.asList("白色", "耳机", "苹果")),
            new MockItem(2004L, "蓝色校园卡", "证件", "崇德楼", Arrays.asList("蓝色", "校园卡", "证件"))
    );

    private static final List<MockItem> LOST_ITEMS = Arrays.asList(
            new MockItem(1001L, "黑色双肩包", "箱包", "图书馆", Arrays.asList("黑色", "双肩包", "帆布")),
            new MockItem(1002L, "华为平板", "电子产品", "新图书馆", Arrays.asList("平板", "华为", "灰色")),
            new MockItem(1003L, "学生证", "证件", "求是楼", Arrays.asList("证件", "学生证")),
            new MockItem(1004L, "黑框眼镜", "眼镜", "致知楼", Arrays.asList("黑色", "眼镜", "圆框"))
    );

    @Override
    public ImageAnalyzeResult analyzeImage(ImageAnalyzeRequest request) {
        String seed = normalize(request.getDescription()) + "|" + normalize(request.getLocationHint()) + "|" + normalize(request.getImageUrl());
        ImageAnalyzeResult result = new ImageAnalyzeResult();

        if (seed.contains("包")) {
            result.setCategory("箱包");
            result.setSubCategory("双肩包");
            result.setTags(Arrays.asList("黑色", "帆布", "双肩包"));
            result.setOcrTexts(Collections.singletonList("CSUFT"));
            result.setBrand("unknown");
            result.setColor("黑色");
            result.setConfidence(0.87);
            result.setRiskLevel("low");
            return result;
        }
        if (seed.contains("卡") || seed.contains("证")) {
            result.setCategory("证件");
            result.setSubCategory("校园卡");
            result.setTags(Arrays.asList("卡片", "蓝色", "证件"));
            result.setOcrTexts(Collections.singletonList("2022****"));
            result.setBrand("unknown");
            result.setColor("蓝色");
            result.setConfidence(0.81);
            result.setRiskLevel("high");
            return result;
        }

        result.setCategory("其他");
        result.setSubCategory("未识别");
        result.setTags(Arrays.asList("待确认", "unknown"));
        result.setOcrTexts(new ArrayList<String>());
        result.setBrand("unknown");
        result.setColor("unknown");
        result.setConfidence(0.62);
        result.setRiskLevel("medium");
        return result;
    }

    @Override
    public List<MatchCandidate> previewMatches(MatchPreviewRequest request) {
        List<MockItem> source = "lost".equalsIgnoreCase(request.getItemType()) ? FOUND_ITEMS : LOST_ITEMS;
        List<MatchCandidate> candidates = new ArrayList<>();
        for (MockItem item : source) {
            int score = calculateScore(request, item);
            if (score < 60) {
                continue;
            }
            List<String> reasons = buildReasons(request, item);
            candidates.add(new MatchCandidate(item.id, item.title, item.location, score, reasons));
        }
        candidates.sort(Comparator.comparingInt(MatchCandidate::getScore).reversed());
        return candidates.size() > 5 ? candidates.subList(0, 5) : candidates;
    }

    private int calculateScore(MatchPreviewRequest request, MockItem item) {
        int score = 40;
        if (normalize(request.getCategory()).equals(normalize(item.category))) {
            score += 20;
        }
        if (normalize(request.getLocation()).equals(normalize(item.location))) {
            score += 15;
        }
        Set<String> inputTags = new HashSet<String>();
        for (String tag : request.getTags()) {
            inputTags.add(normalize(tag));
        }
        int overlap = 0;
        for (String tag : item.tags) {
            if (inputTags.contains(normalize(tag))) {
                overlap++;
            }
        }
        score += Math.min(overlap * 6, 24);

        if (shareChars(request.getTitle(), item.title) >= 2) {
            score += 8;
        }
        return Math.min(score, 99);
    }

    private List<String> buildReasons(MatchPreviewRequest request, MockItem item) {
        List<String> reasons = new ArrayList<String>();
        if (normalize(request.getCategory()).equals(normalize(item.category))) {
            reasons.add("同类物品");
        }
        if (normalize(request.getLocation()).equals(normalize(item.location))) {
            reasons.add("地点接近");
        }
        List<String> overlap = new ArrayList<String>();
        Set<String> inputTags = new HashSet<String>();
        for (String tag : request.getTags()) {
            inputTags.add(normalize(tag));
        }
        for (String tag : item.tags) {
            if (inputTags.contains(normalize(tag))) {
                overlap.add(tag);
            }
        }
        if (!overlap.isEmpty()) {
            reasons.add("共同标签：" + String.join("、", overlap));
        }
        if (reasons.isEmpty()) {
            reasons.add("标题与标签语义接近");
        }
        return reasons;
    }

    private int shareChars(String a, String b) {
        Set<Character> chars = new HashSet<Character>();
        for (char c : String.valueOf(a).toCharArray()) {
            chars.add(c);
        }
        int count = 0;
        for (char c : String.valueOf(b).toCharArray()) {
            if (chars.contains(c)) {
                count++;
            }
        }
        return count;
    }

    private String normalize(String text) {
        return String.valueOf(text).trim().toLowerCase(Locale.ROOT);
    }

    private static class MockItem {
        private final Long id;
        private final String title;
        private final String category;
        private final String location;
        private final List<String> tags;

        private MockItem(Long id, String title, String category, String location, List<String> tags) {
            this.id = id;
            this.title = title;
            this.category = category;
            this.location = location;
            this.tags = tags;
        }
    }
}
