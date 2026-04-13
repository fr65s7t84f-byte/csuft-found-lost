package com.csuft.lostfound.controller;

import com.csuft.lostfound.common.ApiResponse;
import com.csuft.lostfound.common.PageResult;
import com.csuft.lostfound.dto.ClaimApplyRequest;
import com.csuft.lostfound.entity.NotificationRecordEntity;
import com.csuft.lostfound.model.ItemView;
import com.csuft.lostfound.model.MatchCandidate;
import com.csuft.lostfound.security.UserContext;
import com.csuft.lostfound.service.ItemService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserItemController {
    private final ItemService itemService;

    public UserItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/publish/list")
    public ApiResponse<PageResult<ItemView>> publishedList(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long publisherId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ApiResponse.success(itemService.myPublished(type, publisherId, pageNum, pageSize));
    }

    @GetMapping("/items/{type}/{id}")
    public ApiResponse<ItemView> detail(@PathVariable String type, @PathVariable Long id) {
        return ApiResponse.success(itemService.detail(type, id));
    }

    @GetMapping("/items/{type}/{id}/matches")
    public ApiResponse<List<MatchCandidate>> matches(@PathVariable String type, @PathVariable Long id) {
        return ApiResponse.success(itemService.matches(type, id));
    }

    @PostMapping("/items/{type}/{id}/claim")
    public ApiResponse<Long> submitClaim(@PathVariable String type, @PathVariable Long id, @Valid @RequestBody ClaimApplyRequest request) {
        return ApiResponse.success(itemService.submitClaim(type, id, request, UserContext.getUserId()));
    }

    @GetMapping("/notifications")
    public ApiResponse<List<NotificationRecordEntity>> notifications() {
        return ApiResponse.success(itemService.notifications(UserContext.getUserId()));
    }

    @PutMapping("/publish/{type}/{id}/withdraw")
    public ApiResponse<Boolean> withdraw(@PathVariable String type, @PathVariable Long id) {
        return ApiResponse.success(itemService.withdraw(type, id));
    }
}
