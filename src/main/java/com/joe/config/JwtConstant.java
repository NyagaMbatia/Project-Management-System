package com.joe.config;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtConstant {
    public static final String HEADER = "Authorization";

    public static final String SECRET_KEY = generateSecretKey();

    private static String generateSecretKey() {
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
