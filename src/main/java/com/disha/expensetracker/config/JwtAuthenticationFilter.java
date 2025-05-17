package com.disha.expensetracker.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.disha.expensetracker.user.constant.JwtUtil;
import com.disha.expensetracker.user.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        logger.info("JwtAuthenticationFilter: Checking Authorization header");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            logger.info("JwtAuthenticationFilter: Token extracted from header");

            if (jwtUtil.validateToken(token)) {
                logger.info("JwtAuthenticationFilter: Token is valid");
                username = jwtUtil.getUsernameFromToken(token);
                logger.info("JwtAuthenticationFilter: Username from token: {}", username);
            } else {
                logger.warn("JwtAuthenticationFilter: Invalid JWT token");
            }
        } else {
            logger.info("JwtAuthenticationFilter: No Bearer token found in Authorization header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userService.loadUserByUsername(username);

            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.info("JwtAuthenticationFilter: Authentication set in SecurityContext");
        } else if (username == null) {
            logger.info("JwtAuthenticationFilter: Username is null, skipping authentication");
        }

        filterChain.doFilter(request, response);
    }
}
