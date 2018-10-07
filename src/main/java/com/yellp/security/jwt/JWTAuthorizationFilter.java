package com.yellp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yellp.dao.UserEntity;
import com.yellp.repository.UserRepository;
import com.yellp.utils.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final String jwtSecret;

    @Autowired
    private UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        jwtSecret = Resource.PROPERTIES.get("security.jwt.signing-key");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
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
        //parse token
        String username = JWT.require(Algorithm.HMAC256(jwtSecret))
                .build()
                .verify(authorization)
                .getSubject();
        UserEntity userEntity = userRepository.findByUsername(username);
        UsernamePasswordAuthenticationToken token = null;
        if(userEntity != null)
            token = new UsernamePasswordAuthenticationToken(userEntity.getUsername(),userEntity.getPassword(),
                    Collections.emptyList());
        return token;
    }
}
