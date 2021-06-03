package com.atsistemas.superhero.interceptor;

import com.atsistemas.superhero.models.entity.User;
import com.atsistemas.superhero.security.TokerProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer";

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User userCredentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUser(), userCredentials.getPassword()));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = TokerProvider.generateToken(authResult);
        response.addHeader(HEADER_AUTHORIZATION_KEY, TOKEN_BEARER_PREFIX + " " + token);
    }

}
