package com.csuft.lostfound.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_claim_application")
public class ClaimEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "claim_no")
    private String claimNo;
    @Column(name = "application_type")
    private String applicationType;
    @Column(name = "lost_item_id")
    private Long lostItemId;
    @Column(name = "found_item_id")
    private Long foundItemId;
    @Column(name = "target_title")
    private String targetTitle;
    @Column(name = "applicant_user_id")
    private Long applicantUserId;
    @Column(name = "applicant_name")
    private String applicantName;
    @Column(name = "applicant_student_id")
    private String applicantStudentId;
    @Column(name = "applicant_phone")
    private String applicantPhone;
    @Column(name = "evidence_text")
    private String evidenceText;
    @Column(name = "evidence_images")
    private String evidenceImages;
    @Column(name = "match_score")
    private Integer matchScore;
    @Column(name = "reason_summary")
    private String reasonSummary;
    private String status;
    @Column(name = "reviewer_id")
    private Long reviewerId;
    @Column(name = "review_note")
    private String reviewNote;
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClaimNo() { return claimNo; }
    public void setClaimNo(String claimNo) { this.claimNo = claimNo; }
    public String getApplicationType() { return applicationType; }
    public void setApplicationType(String applicationType) { this.applicationType = applicationType; }
    public Long getLostItemId() { return lostItemId; }
    public void setLostItemId(Long lostItemId) { this.lostItemId = lostItemId; }
    public Long getFoundItemId() { return foundItemId; }
    public void setFoundItemId(Long foundItemId) { this.foundItemId = foundItemId; }
    public String getTargetTitle() { return targetTitle; }
    public void setTargetTitle(String targetTitle) { this.targetTitle = targetTitle; }
    public Long getApplicantUserId() { return applicantUserId; }
    public void setApplicantUserId(Long applicantUserId) { this.applicantUserId = applicantUserId; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public String getApplicantStudentId() { return applicantStudentId; }
    public void setApplicantStudentId(String applicantStudentId) { this.applicantStudentId = applicantStudentId; }
    public String getApplicantPhone() { return applicantPhone; }
    public void setApplicantPhone(String applicantPhone) { this.applicantPhone = applicantPhone; }
    public String getEvidenceText() { return evidenceText; }
    public void setEvidenceText(String evidenceText) { this.evidenceText = evidenceText; }
    public String getEvidenceImages() { return evidenceImages; }
    public void setEvidenceImages(String evidenceImages) { this.evidenceImages = evidenceImages; }
    public Integer getMatchScore() { return matchScore; }
    public void setMatchScore(Integer matchScore) { this.matchScore = matchScore; }
    public String getReasonSummary() { return reasonSummary; }
    public void setReasonSummary(String reasonSummary) { this.reasonSummary = reasonSummary; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }
    public String getReviewNote() { return reviewNote; }
    public void setReviewNote(String reviewNote) { this.reviewNote = reviewNote; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getItemId() {
        return foundItemId != null ? foundItemId : lostItemId;
    }

    public String getItemTitle() {
        return targetTitle;
    }

    public String getApplicant() {
        return applicantName;
    }

    public String getStudentId() {
        return applicantStudentId;
    }

    public Integer getScore() {
        return matchScore;
    }
}
