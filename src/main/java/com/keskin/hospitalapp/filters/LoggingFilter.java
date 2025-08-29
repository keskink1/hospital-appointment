package com.keskin.hospitalapp.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //only for test purposes

        System.out.println("Incoming request: " + request.getMethod() + " " + request.getRequestURI() + " from IP: " + request.getRemoteAddr());

        filterChain.doFilter(request, response);

        System.out.println("Outgoing response for " + request.getRequestURI() + " with status: " + response.getStatus());

    }
}
