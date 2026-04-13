package com.csuft.lostfound.service.impl;

import com.csuft.lostfound.entity.FoundItemEntity;
import com.csuft.lostfound.entity.LostItemEntity;
import com.csuft.lostfound.entity.NoticeEntity;
import com.csuft.lostfound.model.AnnouncementBundle;
import com.csuft.lostfound.model.ItemSummary;
import com.csuft.lostfound.model.SimpleNotice;
import com.csuft.lostfound.repository.FoundItemRepository;
import com.csuft.lostfound.repository.LostItemRepository;
import com.csuft.lostfound.repository.NoticeRepository;
import com.csuft.lostfound.service.AnnouncementService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final NoticeRepository noticeRepository;

    public AnnouncementServiceImpl(LostItemRepository lostItemRepository, FoundItemRepository foundItemRepository, NoticeRepository noticeRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.noticeRepository = noticeRepository;
    }

    @Override
    public AnnouncementBundle today() {
        AnnouncementBundle bundle = new AnnouncementBundle();
        List<LostItemEntity> lost = lostItemRepository.findAll().stream()
                .sorted((a, b) -> b.getPublishedAt().compareTo(a.getPublishedAt())).limit(3).collect(Collectors.toList());
        List<FoundItemEntity> found = foundItemRepository.findAll().stream()
                .sorted((a, b) -> b.getPublishedAt().compareTo(a.getPublishedAt())).limit(3).collect(Collectors.toList());
        List<NoticeEntity> notices = noticeRepository.findAll().stream()
                .filter(notice -> "published".equalsIgnoreCase(notice.getStatus()) || "draft".equalsIgnoreCase(notice.getStatus()))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());

        bundle.setTodayLost(lost.stream().map(item -> new ItemSummary(item.getId(), item.getTitle(), item.getLostLocation(), item.getLostTime().format(FORMATTER))).collect(Collectors.toList()));
        bundle.setTodayFound(found.stream().map(item -> new ItemSummary(item.getId(), item.getTitle(), item.getFoundLocation(), item.getFoundTime().format(FORMATTER))).collect(Collectors.toList()));
        bundle.setUrgent(lost.stream().limit(2).map(item -> new SimpleNotice(item.getId(), "紧急寻物：" + item.getTitle(), item.getPublishedAt().format(FORMATTER))).collect(Collectors.toList()));
        bundle.setImportant(notices.stream().limit(2).map(item -> new SimpleNotice(item.getId(), item.getContent(), item.getCreatedAt().format(FORMATTER))).collect(Collectors.toList()));
        return bundle;
    }
}
