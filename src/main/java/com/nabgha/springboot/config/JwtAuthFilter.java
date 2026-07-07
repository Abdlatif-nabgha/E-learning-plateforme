package com.nabgha.springboot.config;


import com.nabgha.springboot.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. extract the header
        final String authHeader = request.getHeader("Authorization");

        // 2. If no token, continue without authenticate
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract JWT from header
        final String jwt =  authHeader.substring(7);

        // 4. Extract the email from token
        final String userEmail = jwtService.extractUsername(jwt);

        // 5. If extract email & user not yet authenticate from context
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 6. Charge user form db
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // 7. Valid the token
            if (jwtService.isTokenValid(jwt, (User) userDetails)) {
                // 8. Create authentication object
                UsernamePasswordAuthenticationToken authToken = new  UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // 9. Add request details
                authToken.setDetails(new WebAuthenticationDetails(request));

                // 10. Set in SecurityContext -> user authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 11. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
