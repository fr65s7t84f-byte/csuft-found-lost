package com.csuft.lostfound.dto;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String account;
    @NotBlank
    private String password;
    private String role;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
