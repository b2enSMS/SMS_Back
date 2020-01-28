package com.b2en.sms.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilter {

	private static final long serialVersionUID = 5836484529628541351L;
	
	private JwtTokenProvider jwtTokenProvider;
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        
        if (token != null) {
            String jwtToken = token.replace(TOKEN_PREFIX, "");
            log.debug("JwtAuthenticationFilter:jwtToken:{}", jwtToken);
            if(jwtTokenProvider.validateToken(jwtToken)){
                Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
            	((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization shall be provided");
            }
        }
        
        chain.doFilter(request, response);
    }
}