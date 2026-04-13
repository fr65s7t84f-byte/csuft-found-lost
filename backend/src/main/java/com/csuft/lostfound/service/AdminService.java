package com.csuft.lostfound.service;

import com.csuft.lostfound.entity.ClaimEntity;
import com.csuft.lostfound.entity.NoticeEntity;
import com.csuft.lostfound.entity.UserEntity;
import com.csuft.lostfound.model.DashboardOverview;

import java.util.List;

public interface AdminService {
    DashboardOverview dashboardOverview();

    List<ClaimEntity> claims();

    boolean reviewClaim(Long id, String status);

    List<UserEntity> users();

    boolean updateUserStatus(Long id, String status);

    List<NoticeEntity> notices();

    boolean createNotice(String title, String content, String status);

    boolean updateNotice(Long id, String title, String content, String status);
}
