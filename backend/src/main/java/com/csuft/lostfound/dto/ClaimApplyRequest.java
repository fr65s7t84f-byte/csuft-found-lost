package com.csuft.lostfound.dto;

import javax.validation.constraints.NotBlank;

public class ClaimApplyRequest {
    @NotBlank
    private String evidenceText;
    private String evidenceImages;

    public String getEvidenceText() {
        return evidenceText;
    }

    public void setEvidenceText(String evidenceText) {
        this.evidenceText = evidenceText;
    }

    public String getEvidenceImages() {
        return evidenceImages;
    }

    public void setEvidenceImages(String evidenceImages) {
        this.evidenceImages = evidenceImages;
    }
}
