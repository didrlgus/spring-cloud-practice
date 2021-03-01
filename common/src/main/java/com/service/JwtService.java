package com.service;

import com.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public boolean isUnValidHeader(String header) {
        return header == null || isNotStartBearer(header);
    }

    public String getPureJwtInHeader(String header) {
        return header.replace(jwtConfig.getPrefix(), "");
    }

    public boolean isNotStartBearer(String header) {
        return !header.startsWith(jwtConfig.getPrefix());
    }

    public Claims getClaimsFromJwt(String jwt) {
        return Jwts.parser()                                                                // check expired time
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(jwt)
                .getBody();
    }

    public String getSubjectFromJwt(String jwt) {
        return Jwts.parser()                                                                // check expired time
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(jwt)
                .getBody().getSubject();
    }

    public List<String> getAuthoritiesFromClaims(Claims claims) {
        String authoritiesStr = String.valueOf(claims.get("authorities"));

        authoritiesStr = authoritiesStr.substring(1, authoritiesStr.length() - 1);

        return Arrays.asList(authoritiesStr.split(","));
    }

}
