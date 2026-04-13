package com.csuft.lostfound.service;

import com.csuft.lostfound.dto.ImageAnalyzeRequest;
import com.csuft.lostfound.dto.MatchPreviewRequest;
import com.csuft.lostfound.model.ImageAnalyzeResult;
import com.csuft.lostfound.model.MatchCandidate;

import java.util.List;

public interface IntelligenceService {
    ImageAnalyzeResult analyzeImage(ImageAnalyzeRequest request);

    List<MatchCandidate> previewMatches(MatchPreviewRequest request);
}
