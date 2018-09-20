package com.yellp.interceptor;

import com.yellp.services.ApiKeyValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Component
public class ApiKeyValidationInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyValidationInterceptor.class);
    private static final String API_KEY_HEADER = "X-Api-Key";

    @Autowired
    private ApiKeyValidationService keyValidationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if(StringUtils.hasText(apiKey) && keyValidationService.validateApiKey(apiKey,request,response)) {
            LOGGER.info("Requested with Api Key: {}",apiKey);
            return true;
        } else {
            LOGGER.error("Requested api key {} not found.",apiKey);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return false;
    }

}
