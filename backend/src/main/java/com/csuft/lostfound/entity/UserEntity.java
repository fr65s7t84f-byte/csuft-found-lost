package com.csuft.lostfound.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    private String phone;
    private String email;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "real_name")
    private String realName;
    private String nickname;
    @Column(name = "avatar_url")
    private String avatarUrl;
    private String gender;
    private String college;
    private String major;
    private String grade;
    private String role;
    private String status;
    @Column(name = "credit_score")
    private Integer creditScore;
    @Column(name = "publish_count")
    private Integer publishCount;
    @Column(name = "success_return_count")
    private Integer successReturnCount;
    @Column(name = "continuous_active_days")
    private Integer continuousActiveDays;
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    @Column(name = "last_login_ip")
    private String lastLoginIp;
    private String remark;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getCreditScore() { return creditScore; }
    public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }
    public Integer getPublishCount() { return publishCount; }
    public void setPublishCount(Integer publishCount) { this.publishCount = publishCount; }
    public Integer getSuccessReturnCount() { return successReturnCount; }
    public void setSuccessReturnCount(Integer successReturnCount) { this.successReturnCount = successReturnCount; }
    public Integer getContinuousActiveDays() { return continuousActiveDays; }
    public void setContinuousActiveDays(Integer continuousActiveDays) { this.continuousActiveDays = continuousActiveDays; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public String getLastLoginIp() { return lastLoginIp; }
    public void setLastLoginIp(String lastLoginIp) { this.lastLoginIp = lastLoginIp; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
