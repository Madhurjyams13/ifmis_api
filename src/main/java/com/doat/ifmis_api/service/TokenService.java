package com.doat.ifmis_api.service;

import com.doat.ifmis_api.config.SecretKeyManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenService {

    private static String SECRET_KEY;

    static {
        // Load secret key when class is first loaded
        SECRET_KEY = SecretKeyManager.loadSecretKey();
    }

    public static String generateToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
