package com.csuft.lostfound.service;

import com.csuft.lostfound.common.PageResult;
import com.csuft.lostfound.dto.ClaimApplyRequest;
import com.csuft.lostfound.dto.PublishItemRequest;
import com.csuft.lostfound.dto.UpdateItemRequest;
import com.csuft.lostfound.entity.NotificationRecordEntity;
import com.csuft.lostfound.model.ItemView;
import com.csuft.lostfound.model.MatchCandidate;

import java.util.List;

public interface ItemService {
    PageResult<ItemView> listItems(String type, String keyword, String category, String location, String tag, String status, String reviewStatus, int pageNum, int pageSize);
    Long publish(String type, PublishItemRequest request);
    boolean update(String type, Long id, UpdateItemRequest request);
    boolean delete(String type, Long id);
    ItemView detail(String type, Long id);
    List<MatchCandidate> matches(String type, Long id);
    PageResult<ItemView> myPublished(String type, Long publisherId, int pageNum, int pageSize);
    boolean withdraw(String type, Long id);
    boolean review(String type, Long id, String reviewStatus);
    Long submitClaim(String type, Long id, ClaimApplyRequest request, Long currentUserId);
    List<NotificationRecordEntity> notifications(Long userId);
}
