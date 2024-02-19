package com.marcuslull.mbyapisec.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtRoleFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtDecoder jwtDecoder;

    public JwtRoleFilter(JwtDecoder jwtDecoder) {
        super(new AntPathRequestMatcher("/api/**"));
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // we need an Authentication object for every connection to check roles and authorizations
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Jwt jwt = jwtDecoder.decode(token);
            String email = jwt.getSubject();
            List<String> roles = jwt.getClaimAsStringList("scope");
            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request,response);
    }
}
