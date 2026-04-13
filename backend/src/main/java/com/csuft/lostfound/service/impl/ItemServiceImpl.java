package com.csuft.lostfound.service.impl;

import com.csuft.lostfound.common.PageResult;
import com.csuft.lostfound.dto.ClaimApplyRequest;
import com.csuft.lostfound.dto.PublishItemRequest;
import com.csuft.lostfound.dto.UpdateItemRequest;
import com.csuft.lostfound.entity.ClaimEntity;
import com.csuft.lostfound.entity.FoundItemEntity;
import com.csuft.lostfound.entity.LostItemEntity;
import com.csuft.lostfound.entity.NotificationRecordEntity;
import com.csuft.lostfound.entity.UserEntity;
import com.csuft.lostfound.model.ItemView;
import com.csuft.lostfound.model.MatchCandidate;
import com.csuft.lostfound.repository.ClaimRepository;
import com.csuft.lostfound.repository.FoundItemRepository;
import com.csuft.lostfound.repository.LostItemRepository;
import com.csuft.lostfound.repository.NotificationRecordRepository;
import com.csuft.lostfound.repository.UserRepository;
import com.csuft.lostfound.service.ItemService;
import com.csuft.lostfound.util.ItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final NotificationRecordRepository notificationRecordRepository;

    public ItemServiceImpl(
            LostItemRepository lostItemRepository,
            FoundItemRepository foundItemRepository,
            ClaimRepository claimRepository,
            UserRepository userRepository,
            NotificationRecordRepository notificationRecordRepository
    ) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
        this.notificationRecordRepository = notificationRecordRepository;
    }

    @Override
    public PageResult<ItemView> listItems(String type, String keyword, String category, String location, String tag, String status, String reviewStatus, int pageNum, int pageSize) {
        if ("lost".equalsIgnoreCase(type)) {
            List<ItemView> list = lostItemRepository.findAll().stream()
                    .map(ItemMapper::fromLost)
                    .filter(item -> matchFilter(item, keyword, category, location, tag, status, reviewStatus))
                    .sorted(Comparator.comparing(ItemView::getPublishedAt, Comparator.nullsLast(String::compareTo)).reversed())
                    .collect(Collectors.toList());
            return page(list, pageNum, pageSize);
        }
        List<ItemView> list = foundItemRepository.findAll().stream()
                .map(ItemMapper::fromFound)
                .filter(item -> matchFilter(item, keyword, category, location, tag, status, reviewStatus))
                .sorted(Comparator.comparing(ItemView::getPublishedAt, Comparator.nullsLast(String::compareTo)).reversed())
                .collect(Collectors.toList());
        return page(list, pageNum, pageSize);
    }

    @Override
    @Transactional
    public Long publish(String type, PublishItemRequest request) {
        if ("lost".equalsIgnoreCase(type)) {
            LostItemEntity entity = new LostItemEntity();
            entity.setLostCode("L" + System.currentTimeMillis());
            fillCommonToLost(entity, request);
            entity.setLostTime(LocalDateTime.now());
            return lostItemRepository.save(entity).getId();
        }
        FoundItemEntity entity = new FoundItemEntity();
        entity.setFoundCode("F" + System.currentTimeMillis());
        fillCommonToFound(entity, request);
        entity.setFoundTime(LocalDateTime.now());
        entity.setStorageLocation("待确认");
        entity.setPickupMethod("线下认领");
        return foundItemRepository.save(entity).getId();
    }

    @Override
    @Transactional
    public boolean update(String type, Long id, UpdateItemRequest request) {
        if ("lost".equalsIgnoreCase(type)) {
            LostItemEntity entity = lostItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在"));
            updateLost(entity, request);
            lostItemRepository.save(entity);
            return true;
        }
        FoundItemEntity entity = foundItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在"));
        updateFound(entity, request);
        foundItemRepository.save(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(String type, Long id) {
        if ("lost".equalsIgnoreCase(type)) {
            lostItemRepository.deleteById(id);
            return true;
        }
        foundItemRepository.deleteById(id);
        return true;
    }

    @Override
    public ItemView detail(String type, Long id) {
        if ("lost".equalsIgnoreCase(type)) {
            return ItemMapper.fromLost(lostItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在")));
        }
        return ItemMapper.fromFound(foundItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在")));
    }

    @Override
    public List<MatchCandidate> matches(String type, Long id) {
        if ("lost".equalsIgnoreCase(type)) {
            ItemView base = detail("lost", id);
            return matchWithList(base, foundItemRepository.findAll().stream().map(ItemMapper::fromFound).collect(Collectors.toList()));
        }
        ItemView base = detail("found", id);
        return matchWithList(base, lostItemRepository.findAll().stream().map(ItemMapper::fromLost).collect(Collectors.toList()));
    }

    @Override
    public PageResult<ItemView> myPublished(String type, Long publisherId, int pageNum, int pageSize) {
        Long owner = publisherId == null ? 2L : publisherId;
        List<ItemView> list = new ArrayList<ItemView>();
        if (isBlank(type) || "lost".equalsIgnoreCase(type)) {
            list.addAll(lostItemRepository.findByPublisherIdOrderByPublishedAtDesc(owner).stream().map(ItemMapper::fromLost).collect(Collectors.toList()));
        }
        if (isBlank(type) || "found".equalsIgnoreCase(type)) {
            list.addAll(foundItemRepository.findByPublisherIdOrderByPublishedAtDesc(owner).stream().map(ItemMapper::fromFound).collect(Collectors.toList()));
        }
        list.sort(Comparator.comparing(ItemView::getPublishedAt, Comparator.nullsLast(String::compareTo)).reversed());
        return page(list, pageNum, pageSize);
    }

    @Override
    @Transactional
    public boolean withdraw(String type, Long id) {
        if ("lost".equalsIgnoreCase(type)) {
            LostItemEntity entity = lostItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在"));
            entity.setWithdrawn(true);
            entity.setWithdrawnAt(LocalDateTime.now());
            entity.setStatus("closed");
            entity.setClosedAt(LocalDateTime.now());
            lostItemRepository.save(entity);
            return true;
        }
        FoundItemEntity entity = foundItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在"));
        entity.setWithdrawn(true);
        entity.setWithdrawnAt(LocalDateTime.now());
        entity.setStatus("closed");
        entity.setClosedAt(LocalDateTime.now());
        foundItemRepository.save(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean review(String type, Long id, String reviewStatus) {
        if ("lost".equalsIgnoreCase(type)) {
            LostItemEntity entity = lostItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在"));
            entity.setReviewStatus(reviewStatus);
            lostItemRepository.save(entity);
            return true;
        }
        FoundItemEntity entity = foundItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("记录不存在"));
        entity.setReviewStatus(reviewStatus);
        foundItemRepository.save(entity);
        return true;
    }

    @Override
    @Transactional
    public Long submitClaim(String type, Long id, ClaimApplyRequest request, Long currentUserId) {
        if (currentUserId == null) {
            throw new IllegalArgumentException("未获取到当前用户信息");
        }
        UserEntity applicant = userRepository.findById(currentUserId).orElseThrow(() -> new IllegalArgumentException("申请用户不存在"));
        ItemView target = detail(type, id);
        ClaimEntity claim = new ClaimEntity();
        claim.setClaimNo("C" + System.currentTimeMillis());
        claim.setApplicationType("found".equalsIgnoreCase(type) ? "lost_claim_found" : "found_claim_lost");
        claim.setTargetTitle(target.getTitle());
        claim.setApplicantUserId(applicant.getId());
        claim.setApplicantName(isBlank(applicant.getRealName()) ? applicant.getNickname() : applicant.getRealName());
        claim.setApplicantStudentId(safe(applicant.getStudentId()));
        claim.setApplicantPhone(safe(applicant.getPhone()));
        claim.setEvidenceText(request.getEvidenceText());
        claim.setEvidenceImages(safe(request.getEvidenceImages()));
        claim.setStatus("pending");
        claim.setSubmittedAt(LocalDateTime.now());
        claim.setUpdatedAt(LocalDateTime.now());

        List<MatchCandidate> candidates = matches(type, id);
        MatchCandidate best = candidates.isEmpty() ? null : candidates.get(0);
        claim.setMatchScore(best == null ? 60 : best.getScore());
        claim.setReasonSummary(best == null ? "用户提交认领证据，待管理员审核" : String.join("、", best.getReasons()));

        Long publisherId;
        if ("found".equalsIgnoreCase(type)) {
            FoundItemEntity entity = foundItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("招领记录不存在"));
            if (Objects.equals(entity.getPublisherId(), applicant.getId())) {
                throw new IllegalArgumentException("不能认领自己发布的招领信息");
            }
            claim.setFoundItemId(id);
            publisherId = entity.getPublisherId();
        } else {
            LostItemEntity entity = lostItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("失物记录不存在"));
            if (Objects.equals(entity.getPublisherId(), applicant.getId())) {
                throw new IllegalArgumentException("不能对自己发布的失物信息重复提交认领");
            }
            claim.setLostItemId(id);
            publisherId = entity.getPublisherId();
        }

        ClaimEntity saved = claimRepository.save(claim);
        saveNotification(applicant.getId(), "claim", saved.getId(), "认领申请已提交", "您提交了“" + target.getTitle() + "”的认领申请，请等待管理员审核。");
        if (publisherId != null) {
            saveNotification(publisherId, "claim", saved.getId(), "收到新的认领申请", "物品“" + target.getTitle() + "”收到新的认领申请，请留意管理员审核结果。");
        }
        return saved.getId();
    }

    @Override
    public List<NotificationRecordEntity> notifications(Long userId) {
        if (userId == null) {
            return new ArrayList<NotificationRecordEntity>();
        }
        return notificationRecordRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    private List<MatchCandidate> matchWithList(ItemView base, List<ItemView> targets) {
        List<MatchCandidate> result = new ArrayList<MatchCandidate>();
        for (ItemView item : targets) {
            int score = score(base, item);
            if (score < 60) continue;
            result.add(new MatchCandidate(item.getId(), item.getTitle(), item.getLocation(), score, reasons(base, item)));
        }
        result.sort(Comparator.comparingInt(MatchCandidate::getScore).reversed());
        return result.size() > 5 ? result.subList(0, 5) : result;
    }

    private int score(ItemView base, ItemView item) {
        int score = 45;
        if (safe(base.getCategory()).equalsIgnoreCase(safe(item.getCategory()))) score += 15;
        if (safe(base.getLocation()).equalsIgnoreCase(safe(item.getLocation()))) score += 10;
        Set<String> baseTags = new HashSet<String>(base.getTags());
        long overlap = item.getTags().stream().filter(baseTags::contains).count();
        score += Math.min((int) overlap * 12, 24);
        if (shareChars(base.getTitle(), item.getTitle()) >= 2) score += 8;
        return Math.min(score, 99);
    }

    private List<String> reasons(ItemView base, ItemView item) {
        List<String> reasons = new ArrayList<String>();
        if (safe(base.getCategory()).equalsIgnoreCase(safe(item.getCategory()))) reasons.add("同类物品：" + base.getCategory());
        if (safe(base.getLocation()).equalsIgnoreCase(safe(item.getLocation()))) reasons.add("地点接近：" + base.getLocation());
        Set<String> baseTags = new HashSet<String>(base.getTags());
        List<String> overlap = item.getTags().stream().filter(baseTags::contains).collect(Collectors.toList());
        if (!overlap.isEmpty()) reasons.add("共同标签：" + String.join("、", overlap));
        if (reasons.isEmpty()) reasons.add("综合标题、描述与标签语义接近");
        return reasons;
    }

    private boolean matchFilter(ItemView item, String keyword, String category, String location, String tag, String status, String reviewStatus) {
        String searchable = (safe(item.getTitle()) + "|" + safe(item.getCategory()) + "|" + safe(item.getLocation()) + "|" + safe(item.getDescription()) + "|" + String.join(",", item.getTags())).toLowerCase(Locale.ROOT);
        if (!isBlank(keyword) && !searchable.contains(keyword.toLowerCase(Locale.ROOT))) return false;
        if (!isBlank(category) && !safe(item.getCategory()).equalsIgnoreCase(category)) return false;
        if (!isBlank(location) && !safe(item.getLocation()).equalsIgnoreCase(location)) return false;
        if (!isBlank(tag) && item.getTags().stream().noneMatch(current -> current.equalsIgnoreCase(tag))) return false;
        if (!isBlank(status) && !safe(item.getStatus()).equalsIgnoreCase(status)) return false;
        if (!isBlank(reviewStatus) && !safe(item.getReviewStatus()).equalsIgnoreCase(reviewStatus)) return false;
        return true;
    }

    private void fillCommonToLost(LostItemEntity entity, PublishItemRequest request) {
        entity.setTitle(request.getTitle());
        entity.setCategory(request.getCategory());
        entity.setSubCategory("");
        entity.setDescription(safe(request.getDescription()));
        entity.setLostLocation(request.getLocation());
        entity.setContactPhone(safe(request.getContact()));
        entity.setContactWechat("");
        entity.setImageUrl(safe(request.getImageUrl()));
        entity.setImageUrls("");
        entity.setAiTags("");
        entity.setManualTags(ItemMapper.joinTags(request.getTags()));
        entity.setOcrText("");
        entity.setBrand("unknown");
        entity.setColor("unknown");
        entity.setMaterial("unknown");
        entity.setUrgencyLevel("normal");
        entity.setStatus("pending_claim");
        entity.setReviewStatus("pending");
        entity.setPublisherId(request.getPublisherId() == null ? 2L : request.getPublisherId());
        entity.setPublisherName(isBlank(request.getPublisherName()) ? "校园用户" : request.getPublisherName());
        entity.setPublishedAt(LocalDateTime.now());
        entity.setWithdrawn(false);
        entity.setExtraJson("");
    }

    private void fillCommonToFound(FoundItemEntity entity, PublishItemRequest request) {
        entity.setTitle(request.getTitle());
        entity.setCategory(request.getCategory());
        entity.setSubCategory("");
        entity.setDescription(safe(request.getDescription()));
        entity.setFoundLocation(request.getLocation());
        entity.setContactPhone(safe(request.getContact()));
        entity.setContactWechat("");
        entity.setImageUrl(safe(request.getImageUrl()));
        entity.setImageUrls("");
        entity.setAiTags("");
        entity.setManualTags(ItemMapper.joinTags(request.getTags()));
        entity.setOcrText("");
        entity.setBrand("unknown");
        entity.setColor("unknown");
        entity.setMaterial("unknown");
        entity.setUrgencyLevel("normal");
        entity.setStatus("pending_claim");
        entity.setReviewStatus("pending");
        entity.setPublisherId(request.getPublisherId() == null ? 2L : request.getPublisherId());
        entity.setPublisherName(isBlank(request.getPublisherName()) ? "校园用户" : request.getPublisherName());
        entity.setPublishedAt(LocalDateTime.now());
        entity.setWithdrawn(false);
        entity.setExtraJson("");
    }

    private void updateLost(LostItemEntity entity, UpdateItemRequest request) {
        if (!isBlank(request.getTitle())) entity.setTitle(request.getTitle());
        if (!isBlank(request.getCategory())) entity.setCategory(request.getCategory());
        if (!isBlank(request.getLocation())) entity.setLostLocation(request.getLocation());
        if (!isBlank(request.getContact())) entity.setContactPhone(request.getContact());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (!isBlank(request.getImageUrl())) entity.setImageUrl(request.getImageUrl());
        if (request.getTags() != null) entity.setManualTags(ItemMapper.joinTags(request.getTags()));
        if (!isBlank(request.getStatus())) entity.setStatus(request.getStatus());
    }

    private void updateFound(FoundItemEntity entity, UpdateItemRequest request) {
        if (!isBlank(request.getTitle())) entity.setTitle(request.getTitle());
        if (!isBlank(request.getCategory())) entity.setCategory(request.getCategory());
        if (!isBlank(request.getLocation())) entity.setFoundLocation(request.getLocation());
        if (!isBlank(request.getContact())) entity.setContactPhone(request.getContact());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (!isBlank(request.getImageUrl())) entity.setImageUrl(request.getImageUrl());
        if (request.getTags() != null) entity.setManualTags(ItemMapper.joinTags(request.getTags()));
        if (!isBlank(request.getStatus())) entity.setStatus(request.getStatus());
    }

    private PageResult<ItemView> page(List<ItemView> items, int pageNum, int pageSize) {
        int page = Math.max(pageNum, 1);
        int size = Math.max(pageSize, 1);
        int from = (page - 1) * size;
        int to = Math.min(from + size, items.size());
        List<ItemView> records = from >= items.size() ? new ArrayList<ItemView>() : items.subList(from, to);
        return new PageResult<ItemView>(records, items.size(), page, size);
    }

    private void saveNotification(Long userId, String bizType, Long bizId, String title, String content) {
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

    private int shareChars(String first, String second) {
        Set<Character> set = new HashSet<Character>();
        for (char c : safe(first).toCharArray()) set.add(c);
        int count = 0;
        for (char c : safe(second).toCharArray()) if (set.contains(c)) count++;
        return count;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

