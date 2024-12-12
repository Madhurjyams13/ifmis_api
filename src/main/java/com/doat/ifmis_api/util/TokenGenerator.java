package com.doat.ifmis_api.util;

import com.doat.ifmis_api.config.SecretKeyManager;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    public static String generateAndSaveSecretKey() {
        // Generate a secure random key
        byte[] keyBytes = new byte[64]; // 512-bit key
        new SecureRandom().nextBytes(keyBytes);

        // Encode the key in Base64
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);

        // Save the key
        SecretKeyManager.saveSecretKey(secretKey);

        return secretKey;
    }

    public static void main(String[] args) {
        String secretKey = generateAndSaveSecretKey();
        System.out.println("Secret key generated and saved: " + secretKey);
    }

}
