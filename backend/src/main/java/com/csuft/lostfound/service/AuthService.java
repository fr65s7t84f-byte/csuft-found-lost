package com.csuft.lostfound.service;

import com.csuft.lostfound.dto.LoginRequest;
import com.csuft.lostfound.dto.RegisterRequest;
import com.csuft.lostfound.model.AuthInfo;

public interface AuthService {
    AuthInfo login(LoginRequest request);

    boolean register(RegisterRequest request);
}
