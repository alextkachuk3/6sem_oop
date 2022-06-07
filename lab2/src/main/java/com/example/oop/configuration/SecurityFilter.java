package com.example.oop.configuration;

import com.example.oop.repository.AdminRepository;
import com.example.oop.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.TokenVerifier;
import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = getUsername(request);
        if (username != null) {
            adminRepository.findByUsername(username).ifPresent(u -> setContext(request, u));
            clientRepository.findByUsername(username).ifPresent(u -> setContext(request, u));
        }
        filterChain.doFilter(request, response);
    }

    public String getUsername(HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String tokenString = authorization.substring("Bearer ".length());
            AccessToken token = TokenVerifier.create(tokenString, AccessToken.class).getToken();
            return token.getPreferredUsername();
        } catch (Exception e) {
            return null;
        }

    }

    private void setContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
