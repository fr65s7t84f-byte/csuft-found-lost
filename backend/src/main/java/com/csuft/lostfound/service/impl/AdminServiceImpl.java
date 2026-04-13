
package com.csuft.lostfound.service.impl;

import com.csuft.lostfound.entity.ClaimEntity;
import com.csuft.lostfound.entity.FoundItemEntity;
import com.csuft.lostfound.entity.LostItemEntity;
import com.csuft.lostfound.entity.NoticeEntity;
import com.csuft.lostfound.entity.NotificationRecordEntity;
import com.csuft.lostfound.entity.UserEntity;
import com.csuft.lostfound.model.DashboardOverview;
import com.csuft.lostfound.repository.ClaimRepository;
import com.csuft.lostfound.repository.FoundItemRepository;
import com.csuft.lostfound.repository.LostItemRepository;
import com.csuft.lostfound.repository.NoticeRepository;
import com.csuft.lostfound.repository.NotificationRecordRepository;
import com.csuft.lostfound.repository.UserRepository;
import com.csuft.lostfound.security.UserContext;
import com.csuft.lostfound.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final NotificationRecordRepository notificationRecordRepository;

    public AdminServiceImpl(
            ClaimRepository claimRepository,
            UserRepository userRepository,
            NoticeRepository noticeRepository,
            LostItemRepository lostItemRepository,
            FoundItemRepository foundItemRepository,
            NotificationRecordRepository notificationRecordRepository
    ) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.notificationRecordRepository = notificationRecordRepository;
    }

    @Override
    public DashboardOverview dashboardOverview() {
        List<LostItemEntity> lost = lostItemRepository.findAll();
        List<FoundItemEntity> found = foundItemRepository.findAll();
        List<ClaimEntity> claims = claimRepository.findAll();
        List<UserEntity> users = userRepository.findAll();

        DashboardOverview overview = new DashboardOverview();
        overview.setTotalItems(lost.size() + found.size());
        overview.setActiveUsers(users.stream().filter(user -> "active".equalsIgnoreCase(user.getStatus())).count());
        long approved = claims.stream().filter(claim -> "approved".equalsIgnoreCase(claim.getStatus())).count();
        overview.setClaimRate(claims.isEmpty() ? 0 : (approved * 100.0 / claims.size()));
        overview.setAvgProcessHours(19.6);
        overview.setTrendLabels(Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        overview.setLostTrend(Arrays.asList(16, 12, 18, 14, 19, 11, 13));
        overview.setFoundTrend(Arrays.asList(8, 11, 7, 9, 13, 10, 6));

        Map<String, Long> counter = new LinkedHashMap<String, Long>();
        lost.forEach(item -> counter.put(item.getCategory(), counter.getOrDefault(item.getCategory(), 0L) + 1L));
        found.forEach(item -> counter.put(item.getCategory(), counter.getOrDefault(item.getCategory(), 0L) + 1L));
        overview.setCategoryNames(new ArrayList<String>(counter.keySet()));
        overview.setCategoryValues(counter.values().stream().map(Long::intValue).collect(Collectors.toList()));
        overview.setRetrievalByType(Arrays.asList(85, 72, 69, 68, 60));
        return overview;
    }

    @Override
    public List<ClaimEntity> claims() {
        return claimRepository.findAll();
    }

    @Override
    @Transactional
    public boolean reviewClaim(Long id, String status) {
        ClaimEntity claim = claimRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("认领记录不存在"));
        claim.setStatus(status);
        claim.setReviewerId(UserContext.getUserId() == null ? 1L : UserContext.getUserId());
        claim.setReviewedAt(LocalDateTime.now());
        claim.setUpdatedAt(LocalDateTime.now());
        claimRepository.save(claim);

        if ("approved".equalsIgnoreCase(status)) {
            updateItemClaimedState(claim);
            rejectOtherPendingClaims(claim);
            saveNotification(claim.getApplicantUserId(), "claim", claim.getId(), "认领申请审核通过", "您提交的“" + claim.getTargetTitle() + "”认领申请已通过，相关物品状态已更新为已认领。");
            Long publisherId = findPublisherId(claim);
            if (publisherId != null) {
                saveNotification(publisherId, "claim", claim.getId(), "认领申请已通过", "物品“" + claim.getTargetTitle() + "”的认领申请已审核通过，请及时线下交接。");
            }
            return true;
        }

        saveNotification(claim.getApplicantUserId(), "claim", claim.getId(), "认领申请未通过", "您提交的“" + claim.getTargetTitle() + "”认领申请未通过，请补充更充分的证据后再试。");
        return true;
    }

    @Override
    public List<UserEntity> users() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean updateUserStatus(Long id, String status) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        user.setStatus(status);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    @Override
    public List<NoticeEntity> notices() {
        return noticeRepository.findAll();
    }

    @Override
    @Transactional
    public boolean createNotice(String title, String content, String status) {
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setNoticeType("system");
        notice.setScope("all");
        notice.setPriority(1);
        notice.setStatus(status == null || status.trim().isEmpty() ? "draft" : status);
        notice.setPublishAt("published".equalsIgnoreCase(notice.getStatus()) ? LocalDateTime.now() : null);
        notice.setExpireAt(null);
        notice.setCreatedBy(1L);
        notice.setUpdatedBy(1L);
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedAt(LocalDateTime.now());
        noticeRepository.save(notice);
        return true;
    }

    @Override
    @Transactional
    public boolean updateNotice(Long id, String title, String content, String status) {
        NoticeEntity notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("公告不存在"));
        if (title != null) notice.setTitle(title);
        if (content != null) notice.setContent(content);
        if (status != null && !status.trim().isEmpty()) {
            notice.setStatus(status);
            notice.setPublishAt("published".equalsIgnoreCase(status) ? LocalDateTime.now() : notice.getPublishAt());
        }
        notice.setUpdatedBy(1L);
        notice.setUpdatedAt(LocalDateTime.now());
        noticeRepository.save(notice);
        return true;
    }

    private void updateItemClaimedState(ClaimEntity claim) {
        LocalDateTime now = LocalDateTime.now();
        if (claim.getLostItemId() != null) {
            LostItemEntity lostItem = lostItemRepository.findById(claim.getLostItemId()).orElse(null);
            if (lostItem != null) {
                lostItem.setStatus("claimed");
                if (!"approved".equalsIgnoreCase(lostItem.getReviewStatus())) {
                    lostItem.setReviewStatus("approved");
                }
                lostItem.setClosedAt(now);
                lostItemRepository.save(lostItem);
            }
        }
        if (claim.getFoundItemId() != null) {
            FoundItemEntity foundItem = foundItemRepository.findById(claim.getFoundItemId()).orElse(null);
            if (foundItem != null) {
                foundItem.setStatus("claimed");
                if (!"approved".equalsIgnoreCase(foundItem.getReviewStatus())) {
                    foundItem.setReviewStatus("approved");
                }
                foundItem.setClosedAt(now);
                foundItemRepository.save(foundItem);
            }
        }
    }

    private void rejectOtherPendingClaims(ClaimEntity currentClaim) {
        List<ClaimEntity> relatedClaims = claimRepository.findAll().stream()
                .filter(claim -> !Objects.equals(claim.getId(), currentClaim.getId()))
                .filter(claim -> "pending".equalsIgnoreCase(claim.getStatus()))
                .filter(claim -> (currentClaim.getLostItemId() != null && Objects.equals(claim.getLostItemId(), currentClaim.getLostItemId())) || (currentClaim.getFoundItemId() != null && Objects.equals(claim.getFoundItemId(), currentClaim.getFoundItemId())))
                .collect(Collectors.toList());
        for (ClaimEntity claim : relatedClaims) {
            claim.setStatus("rejected");
            claim.setReviewNote("同物品已有其他认领申请审核通过");
            claim.setReviewedAt(LocalDateTime.now());
            claim.setUpdatedAt(LocalDateTime.now());
            claimRepository.save(claim);
            saveNotification(claim.getApplicantUserId(), "claim", claim.getId(), "认领申请未通过", "由于该物品已有其他认领申请审核通过，您的申请已自动关闭。");
        }
    }

    private Long findPublisherId(ClaimEntity claim) {
        if (claim.getLostItemId() != null) {
            LostItemEntity lostItem = lostItemRepository.findById(claim.getLostItemId()).orElse(null);
            return lostItem == null ? null : lostItem.getPublisherId();
        }
        if (claim.getFoundItemId() != null) {
            FoundItemEntity foundItem = foundItemRepository.findById(claim.getFoundItemId()).orElse(null);
            return foundItem == null ? null : foundItem.getPublisherId();
        }
        return null;
    }

    private void saveNotification(Long userId, String bizType, Long bizId, String title, String content) {
        if (userId == null) {
            return;
        }
        NotificationRecordEntity notification = new NotificationRecordEntity();
        notification.setUserId(userId);
        notification.setBizType(bizType);
        notification.setBizId(bizId);
        notification.setChannel("site");
        notification.setTitle(title);
        notification.setContent(content);
        notification.setRead(false);
        notification.setSendStatus("sent");
        notification.setErrorMessage("");
        notification.setCreatedAt(LocalDateTime.now());
        notificationRecordRepository.save(notification);
    }
}
