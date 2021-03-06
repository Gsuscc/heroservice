package com.reactheroes.zuul.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    private static final String TOKEN_KEY_JWT = "token";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    String getUsernameFromToken(String token) throws UsernameNotFoundException {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return body.getSubject();
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
//            log.debug("JWT token invalid " + e);
        }
        return false;
    }

    Authentication parseUserFromTokenInfo(String token) throws UsernameNotFoundException {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = body.getSubject();
        return new UsernamePasswordAuthenticationToken(username, "");
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(TOKEN_KEY_JWT))
                .findFirst()
                .map(Cookie::getValue).orElse(null);
    }

}