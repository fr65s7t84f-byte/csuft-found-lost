package com.csuft.lostfound.controller;

import com.csuft.lostfound.common.ApiResponse;
import com.csuft.lostfound.dto.NoticeRequest;
import com.csuft.lostfound.dto.ReviewRequest;
import com.csuft.lostfound.dto.StatusRequest;
import com.csuft.lostfound.entity.ClaimEntity;
import com.csuft.lostfound.entity.NoticeEntity;
import com.csuft.lostfound.entity.UserEntity;
import com.csuft.lostfound.model.DashboardOverview;
import com.csuft.lostfound.service.AdminService;
import com.csuft.lostfound.service.ItemService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final ItemService itemService;

    public AdminController(AdminService adminService, ItemService itemService) {
        this.adminService = adminService;
        this.itemService = itemService;
    }

    @GetMapping("/dashboard/overview")
    public ApiResponse<DashboardOverview> dashboardOverview() {
        return ApiResponse.success(adminService.dashboardOverview());
    }

    @GetMapping("/claims")
    public ApiResponse<List<ClaimEntity>> claims() {
        return ApiResponse.success(adminService.claims());
    }

    @PutMapping("/claims/{id}/review")
    public ApiResponse<Boolean> reviewClaim(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return ApiResponse.success(adminService.reviewClaim(id, request.getStatus()));
    }

    @GetMapping("/users")
    public ApiResponse<List<UserEntity>> users() {
        return ApiResponse.success(adminService.users());
    }

    @PutMapping("/users/{id}")
    public ApiResponse<Boolean> updateUserStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return ApiResponse.success(adminService.updateUserStatus(id, request.getStatus()));
    }

    @GetMapping("/notices")
    public ApiResponse<List<NoticeEntity>> notices() {
        return ApiResponse.success(adminService.notices());
    }

    @PostMapping("/notices")
    public ApiResponse<Boolean> createNotice(@Valid @RequestBody NoticeRequest request) {
        return ApiResponse.success(adminService.createNotice(request.getTitle(), request.getContent(), request.getStatus()));
    }

    @PutMapping("/notices/{id}")
    public ApiResponse<Boolean> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeRequest request) {
        return ApiResponse.success(adminService.updateNotice(id, request.getTitle(), request.getContent(), request.getStatus()));
    }

    @PutMapping("/lost-items/{id}/review")
    public ApiResponse<Boolean> reviewLost(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        return ApiResponse.success(itemService.review("lost", id, request.getReviewStatus()));
    }

    @PutMapping("/found-items/{id}/review")
    public ApiResponse<Boolean> reviewFound(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        return ApiResponse.success(itemService.review("found", id, request.getReviewStatus()));
    }
}
