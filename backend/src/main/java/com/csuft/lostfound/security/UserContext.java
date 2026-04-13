package com.csuft.lostfound.security;

public class UserContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<Long>();
    private static final ThreadLocal<String> USER_ROLE = new ThreadLocal<String>();

    public static void set(Long userId, String role) {
        USER_ID.set(userId);
        USER_ROLE.set(role);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static String getRole() {
        return USER_ROLE.get();
    }

    public static void clear() {
        USER_ID.remove();
        USER_ROLE.remove();
    }
}
