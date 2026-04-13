package com.csuft.lostfound.controller;

import com.csuft.lostfound.common.ApiResponse;
import com.csuft.lostfound.model.AnnouncementBundle;
import com.csuft.lostfound.service.AnnouncementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/today")
    public ApiResponse<AnnouncementBundle> today() {
        return ApiResponse.success(announcementService.today());
    }
}
