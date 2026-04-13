package com.csuft.lostfound.model;

import java.util.ArrayList;
import java.util.List;

public class ImageAnalyzeResult {
    private String category;
    private String subCategory;
    private List<String> tags = new ArrayList<>();
    private List<String> ocrTexts = new ArrayList<>();
    private String brand;
    private String color;
    private double confidence;
    private String riskLevel;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getOcrTexts() {
        return ocrTexts;
    }

    public void setOcrTexts(List<String> ocrTexts) {
        this.ocrTexts = ocrTexts;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
