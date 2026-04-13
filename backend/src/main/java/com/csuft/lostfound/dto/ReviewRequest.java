package com.csuft.lostfound.dto;

import javax.validation.constraints.NotBlank;

public class ReviewRequest {
    @NotBlank
    private String reviewStatus;

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
