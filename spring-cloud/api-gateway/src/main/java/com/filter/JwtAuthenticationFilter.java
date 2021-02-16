package com.filter;

import com.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getHeader());

        if (isUnValidHeader(header)) {
            filterChain.doFilter(request, response);                                        // go to the next filter in filter chain
            return;
        }

        String jwt = header.replace(jwtConfig.getPrefix(), "");                   // remove Bearer and get pure jwt

        try {
            authenticationWithJwt(jwt);
        } catch (Exception e) {
            clearContextHolder();
        }

        filterChain.doFilter(request, response);                                            // go to the next filter in filter chain
    }

    public void authenticationWithJwt(String jwt) {
        Claims claims = getClaimsFromJwt(jwt);

        String identifier = claims.getSubject();

        if (identifier != null) {
            setAuthenticationToContextHolder(identifier, claims);
        }
    }

    public void setAuthenticationToContextHolder(String identifier, Claims claims) {
        SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(identifier, claims));
    }

    public void clearContextHolder() {
        SecurityContextHolder.clearContext();
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String identifier, Claims claims) {
        return new UsernamePasswordAuthenticationToken(identifier, null,
                getAuthoritiesFromClaims(claims).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    public List<String> getAuthoritiesFromClaims(Claims claims) {
        String authoritiesStr = String.valueOf(claims.get("authorities"));

        authoritiesStr = authoritiesStr.substring(1, authoritiesStr.length() - 1);

        return Arrays.asList(authoritiesStr.split(","));
    }

    public boolean isUnValidHeader(String header) {
        return header == null || isNotStartBearer(header);
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

}
