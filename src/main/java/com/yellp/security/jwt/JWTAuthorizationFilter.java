package com.yellp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yellp.dao.UserSessionEntity;
import com.yellp.services.UserSessionService;
import com.yellp.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final String tokenPrefix = "Bearer ";

    private final UserSessionService sessionService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,UserSessionService sessionService) {
        super(authenticationManager);
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken token = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isEmpty(authorization))
            return null;
        String jwt = authorization.replace(tokenPrefix,"");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET.value())).build().verify(jwt);
        String username = decodedJWT.getSubject();
        String sessionId = decodedJWT.getId();
        Optional<UserSessionEntity> session = sessionService.getActiveSessionForUser(username);
        UsernamePasswordAuthenticationToken token = null;
        if(session.isPresent() && sessionId.equals(session.get().getSessionId()))
            token = new UsernamePasswordAuthenticationToken(session.get().getUsername(),null,
                    Collections.emptyList());
        return token;
    }
}
