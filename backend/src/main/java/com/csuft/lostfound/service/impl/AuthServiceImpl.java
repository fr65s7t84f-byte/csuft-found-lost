package com.csuft.lostfound.service.impl;

import com.csuft.lostfound.dto.LoginRequest;
import com.csuft.lostfound.dto.RegisterRequest;
import com.csuft.lostfound.entity.UserEntity;
import com.csuft.lostfound.entity.UserSessionEntity;
import com.csuft.lostfound.model.AuthInfo;
import com.csuft.lostfound.repository.UserRepository;
import com.csuft.lostfound.repository.UserSessionRepository;
import com.csuft.lostfound.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    public AuthServiceImpl(UserRepository userRepository, UserSessionRepository userSessionRepository) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    @Transactional
    public AuthInfo login(LoginRequest request) {
        UserEntity user = findLoginUser(request.getAccount());
        if (!safe(user.getPasswordHash()).equals(request.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        if (request.getRole() != null && !request.getRole().trim().isEmpty() && !request.getRole().equals(user.getRole())) {
            throw new IllegalArgumentException("角色不匹配");
        }
        if (!"active".equalsIgnoreCase(user.getStatus())) {
            throw new IllegalArgumentException("用户状态不可用");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime now = LocalDateTime.now();
        UserSessionEntity session = new UserSessionEntity();
        session.setUserId(user.getId());
        session.setToken(token);
        session.setIssuedAt(now);
        session.setExpiredAt(now.plusDays(7));
        session.setLastAccessAt(now);
        session.setStatus("active");
        session.setLoginIp(getClientIp());
        session.setUserAgent(getUserAgent());
        userSessionRepository.save(session);

        user.setLastLoginAt(now);
        user.setLastLoginIp(getClientIp());
        user.setUpdatedAt(now);
        userRepository.save(user);

        return new AuthInfo(token, user.getRole(), user.getNickname(), safe(user.getAvatarUrl()));
    }

    @Override
    @Transactional
    public boolean register(RegisterRequest request) {
        String username = safe(request.getUsername());
        String phone = safe(request.getPhone());
        if (username.isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (phone.isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("用户名已存在");
        }
        if (userRepository.findByPhone(phone).isPresent()) {
            throw new IllegalArgumentException("手机号已存在");
        }

        LocalDateTime now = LocalDateTime.now();
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPasswordHash(request.getPassword());
        user.setPhone(phone);
        user.setEmail("");
        user.setStudentId("");
        user.setRealName(username);
        user.setNickname(username);
        user.setAvatarUrl("");
        user.setGender("unknown");
        user.setCollege("未填写");
        user.setMajor("未填写");
        user.setGrade("未填写");
        user.setRole("user");
        user.setStatus("active");
        user.setCreditScore(100);
        user.setPublishCount(0);
        user.setSuccessReturnCount(0);
        user.setContinuousActiveDays(0);
        user.setLastLoginAt(null);
        user.setLastLoginIp("");
        user.setRemark("");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userRepository.save(user);
        return true;
    }

    private UserEntity findLoginUser(String loginAccount) {
        String input = safe(loginAccount);
        Optional<UserEntity> optional = userRepository.findByUsername(input);
        if (!optional.isPresent()) optional = userRepository.findByPhone(input);
        if (!optional.isPresent()) optional = userRepository.findByNickname(input);
        if (!optional.isPresent()) optional = userRepository.findByStudentId(input);
        return optional.orElseThrow(() -> new IllegalArgumentException("账号不存在"));
    }

    private String getClientIp() {
        HttpServletRequest request = currentRequest();
        return request == null ? "" : safe(request.getRemoteAddr());
    }

    private String getUserAgent() {
        HttpServletRequest request = currentRequest();
        return request == null ? "" : safe(request.getHeader("User-Agent"));
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
