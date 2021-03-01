package com.filter;

import com.config.JwtConfig;
import com.service.JwtService;
import io.jsonwebtoken.Claims;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getHeader());

        if (jwtService.isUnValidHeader(header)) {
            filterChain.doFilter(request, response);                                        // go to the next filter in filter chain
            return;
        }

        String jwt = jwtService.getPureJwtInHeader(header);                                 // remove Bearer and get pure jwt

        try {
            authenticationWithJwt(jwt);
        } catch (Exception e) {
            clearContextHolder();
        }

        filterChain.doFilter(request, response);                                            // go to the next filter in filter chain
    }

    public void authenticationWithJwt(String jwt) {
        Claims claims = jwtService.getClaimsFromJwt(jwt);

        String identifier = jwtService.getSubjectFromJwt(jwt);

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
                jwtService.getAuthoritiesFromClaims(claims).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

}
