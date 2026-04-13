package com.csuft.lostfound.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_lost_item")
public class LostItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lost_code")
    private String lostCode;
    private String title;
    private String category;
    @Column(name = "sub_category")
    private String subCategory;
    private String description;
    @Column(name = "lost_time")
    private LocalDateTime lostTime;
    @Column(name = "lost_location")
    private String lostLocation;
    @Column(name = "location_lat")
    private BigDecimal locationLat;
    @Column(name = "location_lng")
    private BigDecimal locationLng;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "contact_wechat")
    private String contactWechat;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "image_urls")
    private String imageUrls;
    @Column(name = "ai_tags")
    private String aiTags;
    @Column(name = "manual_tags")
    private String manualTags;
    @Column(name = "ocr_text")
    private String ocrText;
    private String brand;
    private String color;
    private String material;
    @Column(name = "reward_amount")
    private BigDecimal rewardAmount;
    @Column(name = "urgency_level")
    private String urgencyLevel;
    private String status;
    @Column(name = "review_status")
    private String reviewStatus;
    @Column(name = "publisher_id")
    private Long publisherId;
    @Column(name = "publisher_name")
    private String publisherName;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    private Boolean withdrawn;
    @Column(name = "withdrawn_at")
    private LocalDateTime withdrawnAt;
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    @Column(name = "extra_json")
    private String extraJson;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLostCode() { return lostCode; }
    public void setLostCode(String lostCode) { this.lostCode = lostCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getLostTime() { return lostTime; }
    public void setLostTime(LocalDateTime lostTime) { this.lostTime = lostTime; }
    public String getLostLocation() { return lostLocation; }
    public void setLostLocation(String lostLocation) { this.lostLocation = lostLocation; }
    public BigDecimal getLocationLat() { return locationLat; }
    public void setLocationLat(BigDecimal locationLat) { this.locationLat = locationLat; }
    public BigDecimal getLocationLng() { return locationLng; }
    public void setLocationLng(BigDecimal locationLng) { this.locationLng = locationLng; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getContactWechat() { return contactWechat; }
    public void setContactWechat(String contactWechat) { this.contactWechat = contactWechat; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getImageUrls() { return imageUrls; }
    public void setImageUrls(String imageUrls) { this.imageUrls = imageUrls; }
    public String getAiTags() { return aiTags; }
    public void setAiTags(String aiTags) { this.aiTags = aiTags; }
    public String getManualTags() { return manualTags; }
    public void setManualTags(String manualTags) { this.manualTags = manualTags; }
    public String getOcrText() { return ocrText; }
    public void setOcrText(String ocrText) { this.ocrText = ocrText; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public BigDecimal getRewardAmount() { return rewardAmount; }
    public void setRewardAmount(BigDecimal rewardAmount) { this.rewardAmount = rewardAmount; }
    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }
    public Long getPublisherId() { return publisherId; }
    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }
    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public Boolean getWithdrawn() { return withdrawn; }
    public void setWithdrawn(Boolean withdrawn) { this.withdrawn = withdrawn; }
    public LocalDateTime getWithdrawnAt() { return withdrawnAt; }
    public void setWithdrawnAt(LocalDateTime withdrawnAt) { this.withdrawnAt = withdrawnAt; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }
    public String getExtraJson() { return extraJson; }
    public void setExtraJson(String extraJson) { this.extraJson = extraJson; }
}
