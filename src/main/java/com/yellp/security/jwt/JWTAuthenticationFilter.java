package com.yellp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellp.dao.UserEntity;
import com.yellp.dao.UserSessionEntity;
import com.yellp.services.UserSessionService;
import com.yellp.utils.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    private final UserSessionService sessionService;

    private UserEntity user;

    private String jwtSecret;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,UserSessionService sessionService) {
        this.authenticationManager = authenticationManager;
        this.sessionService = sessionService;
        jwtSecret = Resource.PROPERTIES.get("security.jwt.signing-key");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            user = new ObjectMapper().readValue(request.getInputStream(),UserEntity.class);
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
        UserSessionEntity session = new UserSessionEntity();
        session.setUsername(user.getUsername());
        sessionService.getActiveSessionForUser(user.getUsername())
                .ifPresent(entity->sessionService.invalidateSession(entity.getSessionId()));
        session = Objects.requireNonNull(sessionService.createSession(session));
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(session.getExpires())
                .withJWTId(session.getSessionId())
                .sign(Algorithm.HMAC256(jwtSecret));
        LOGGER.debug("Generated token for user {} : {}",authResult.getPrincipal(),token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BufferedWriter writer = new BufferedWriter(response.getWriter());
        writer.write(String.format("{\"status\":\"success\",\"token\":\"%s\"}",token));
        writer.flush();
    }

}
