package com.csuft.lostfound.model;

public class AuthInfo {
    private String token;
    private String role;
    private String name;
    private String avatarUrl;

    public AuthInfo() {
    }

    public AuthInfo(String token, String role, String name, String avatarUrl) {
        this.token = token;
        this.role = role;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
