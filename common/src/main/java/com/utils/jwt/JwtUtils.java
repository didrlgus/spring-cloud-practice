package com.utils.jwt;

import com.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtUtils {

    private final JwtConfig jwtConfig;

    public boolean isUnValidHeader(String header) {
        return Optional.ofNullable(header)
                .map(val -> !val.startsWith(jwtConfig.getPrefix()))
                .orElse(true);
    }

    public String getPureJwtInHeader(String header) {
        return header.replace(jwtConfig.getPrefix(), "");
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

    public String getJwtFromRequest(HttpServletRequest request) throws AccessDeniedException {
        String token = request.getHeader(jwtConfig.getHeader());

        if(isUnValidHeader(token)) {
            throw new AccessDeniedException("유효하지 않은 토큰입니다.");
        }

        return getPureJwtInHeader(token);
    }

    public String getIdentifierFromJwt(String jwt) {
        return getSubjectFromJwt(jwt);
    }

}
