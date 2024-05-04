package io.svinoczar.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.svinoczar.api.exception.AuthException;
import io.svinoczar.api.exception.UnauthorizedException;
import reactor.core.publisher.Mono;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtHandler {

    private final String secret;
//    HashMap
    public JwtHandler(String secret) {
        this.secret = secret;
    }

    public Mono<VerificationResult> check(String accessToken) {
        return Mono.just(verify(accessToken))
                .onErrorResume(e -> Mono.error(new UnauthorizedException(e.getMessage())));
    }

    private VerificationResult verify(String token) {
        Claims claims = getClaimsFromToken(token);
        final Date expirationDate = claims.getExpiration();
        System.out.println(expirationDate);

        if(expirationDate.before(new Date())) throw new RuntimeException("Token expired");

        return new VerificationResult(claims, token);
    }

    private Claims getClaimsFromToken(String token) {
        System.out.println("TOKEN IN `getClaimsFromToken`: " + token);
        try {
            Claims result = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
            return result;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    public static class VerificationResult {
        public Claims claims;
        public String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}
