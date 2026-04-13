package com.csuft.lostfound.controller;

import com.csuft.lostfound.common.ApiResponse;
import com.csuft.lostfound.dto.ImageAnalyzeRequest;
import com.csuft.lostfound.dto.MatchPreviewRequest;
import com.csuft.lostfound.model.ImageAnalyzeResult;
import com.csuft.lostfound.model.MatchCandidate;
import com.csuft.lostfound.service.IntelligenceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/intelligence")
public class IntelligenceController {
    private final IntelligenceService intelligenceService;

    public IntelligenceController(IntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping("/analyze-image")
    public ApiResponse<ImageAnalyzeResult> analyzeImage(@Valid @RequestBody ImageAnalyzeRequest request) {
        return ApiResponse.success(intelligenceService.analyzeImage(request));
    }

    @PostMapping("/match-preview")
    public ApiResponse<List<MatchCandidate>> matchPreview(@Valid @RequestBody MatchPreviewRequest request) {
        return ApiResponse.success(intelligenceService.previewMatches(request));
    }
}
