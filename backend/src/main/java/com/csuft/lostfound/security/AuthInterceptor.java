package com.csuft.lostfound.security;

import com.csuft.lostfound.entity.UserEntity;
import com.csuft.lostfound.entity.UserSessionEntity;
import com.csuft.lostfound.repository.UserRepository;
import com.csuft.lostfound.repository.UserSessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    public AuthInterceptor(UserSessionRepository userSessionRepository, UserRepository userRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        if (!path.startsWith("/api/")) {
            return true;
        }
        if (path.equals("/api/user/login") || path.equals("/api/auth/register")) {
            return true;
        }
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            writeUnauthorized(response, "未登录");
            return false;
        }
        String token = auth.substring("Bearer ".length()).trim();
        Optional<UserSessionEntity> sessionOpt = userSessionRepository.findByTokenAndStatus(token, "active");
        if (!sessionOpt.isPresent()) {
            writeUnauthorized(response, "登录态失效");
            return false;
        }
        UserSessionEntity session = sessionOpt.get();
        if (session.getExpiredAt().isBefore(LocalDateTime.now())) {
            writeUnauthorized(response, "登录已过期");
            return false;
        }
        UserEntity user = userRepository.findById(session.getUserId()).orElse(null);
        if (user == null || !"active".equalsIgnoreCase(user.getStatus())) {
            writeUnauthorized(response, "用户不可用");
            return false;
        }
        if (path.startsWith("/api/admin/") && !"admin".equalsIgnoreCase(user.getRole())) {
            writeForbidden(response, "无管理员权限");
            return false;
        }
        session.setLastAccessAt(LocalDateTime.now());
        userSessionRepository.save(session);
        UserContext.set(user.getId(), user.getRole());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(401);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"" + message + "\",\"data\":null}");
    }

    private void writeForbidden(HttpServletResponse response, String message) throws Exception {
        response.setStatus(403);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":403,\"message\":\"" + message + "\",\"data\":null}");
    }
}
