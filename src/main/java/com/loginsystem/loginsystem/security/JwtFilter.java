package com.loginsystem.loginsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil util;

    @Autowired
    UserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String authorization = req.getHeader("Authorization");
        try {
            if (authorization != null) {
                String token = authorization.split(" ")[1];
                String userName = util.extractUserName(token);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = service.loadUserByUsername(userName);
                    if (util.validateToken(token, userDetails)) {
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword())
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(req, resp); // Continue the request
    }
}
