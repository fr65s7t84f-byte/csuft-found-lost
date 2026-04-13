package com.csuft.lostfound.util;

import com.csuft.lostfound.entity.FoundItemEntity;
import com.csuft.lostfound.entity.LostItemEntity;
import com.csuft.lostfound.model.ItemView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static ItemView fromLost(LostItemEntity entity) {
        ItemView view = new ItemView();
        view.setId(entity.getId());
        view.setType("lost");
        view.setTitle(entity.getTitle());
        view.setCategory(entity.getCategory());
        view.setLocation(entity.getLostLocation());
        view.setContact(entity.getContactPhone());
        view.setDescription(entity.getDescription());
        view.setTags(parseTags(preferredTags(entity.getManualTags(), entity.getAiTags())));
        view.setImageUrl(entity.getImageUrl());
        view.setLostOrFoundTime(format(entity.getLostTime()));
        view.setPublishedAt(format(entity.getPublishedAt()));
        view.setPublisherId(entity.getPublisherId());
        view.setPublisherName(entity.getPublisherName());
        view.setStatus(entity.getStatus());
        view.setReviewStatus(entity.getReviewStatus());
        view.setWithdrawn(Boolean.TRUE.equals(entity.getWithdrawn()));
        return view;
    }

    public static ItemView fromFound(FoundItemEntity entity) {
        ItemView view = new ItemView();
        view.setId(entity.getId());
        view.setType("found");
        view.setTitle(entity.getTitle());
        view.setCategory(entity.getCategory());
        view.setLocation(entity.getFoundLocation());
        view.setContact(entity.getContactPhone());
        view.setDescription(entity.getDescription());
        view.setTags(parseTags(preferredTags(entity.getManualTags(), entity.getAiTags())));
        view.setImageUrl(entity.getImageUrl());
        view.setLostOrFoundTime(format(entity.getFoundTime()));
        view.setPublishedAt(format(entity.getPublishedAt()));
        view.setPublisherId(entity.getPublisherId());
        view.setPublisherName(entity.getPublisherName());
        view.setStatus(entity.getStatus());
        view.setReviewStatus(entity.getReviewStatus());
        view.setWithdrawn(Boolean.TRUE.equals(entity.getWithdrawn()));
        return view;
    }

    public static String joinTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return tags.stream().map(String::trim).filter(value -> !value.isEmpty()).collect(Collectors.joining(","));
    }

    public static List<String> parseTags(String tags) {
        if (tags == null || tags.trim().isEmpty()) {
            return new ArrayList<String>();
        }
        return Arrays.stream(tags.split(",")).map(String::trim).filter(value -> !value.isEmpty()).collect(Collectors.toList());
    }

    private static String preferredTags(String manualTags, String aiTags) {
        if (manualTags != null && !manualTags.trim().isEmpty()) return manualTags;
        return aiTags == null ? "" : aiTags;
    }

    private static String format(LocalDateTime time) {
        return time == null ? null : time.format(FORMATTER);
    }
}
