package com.blog.api.config;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private SecretKey cachedKey;
    private static final int EXPIRATION = 10 * 60 * 1000;

    // 키값은 환경변수 파일에 넣는게 좋음. 난 구현해볼려는거니깐
    private final String secretKey = "skskskskskskeyeyeyeyeycuciruciauiwe";

    private SecretKey _getSecretKey() {
        String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(base64EncodedKey.getBytes());
    }

    public SecretKey getSecretKey() {
        if (cachedKey == null) {
            cachedKey = _getSecretKey();
        }
        return cachedKey;
    }

    public Date getExpiration() {
        return new Date(System.currentTimeMillis() + EXPIRATION);
    }
}
