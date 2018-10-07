package com.yellp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellp.dao.UserEntity;
import com.yellp.utils.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthFilter.class);

    private final AuthenticationManager authenticationManager;

    private String jwtSecret;

    public JWTAuthFilter(@Autowired AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        jwtSecret = Resource.PROPERTIES.get("security.jwt.signing-key");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(),UserEntity.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(), Collections.emptyList());
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            LOGGER.error("Error while parsing request.",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(authResult.getPrincipal().toString())
                .withExpiresAt(Date.from(Instant.now().plus(Duration.ofHours(24))))
                .sign(Algorithm.HMAC256(jwtSecret));
        LOGGER.debug("Generated token for user {} : {}",authResult.getPrincipal(),token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BufferedWriter writer = new BufferedWriter(response.getWriter());
        writer.write(String.format("{\"status\":\"success\",\"token\":\"%s\"}",token));
        writer.flush();
    }
}
