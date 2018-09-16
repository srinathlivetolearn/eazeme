package com.yellp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Component
public class ApiKeyValidationInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyValidationInterceptor.class);
    private static final String API_KEY_HEADER = "X-Api-Key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);
        LOGGER.info(String.format("Requested with api key %s .",apiKey));
        return true;
    }
}
