package com.yellp.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApiKeyValidationService {
    boolean validateApiKey(String apiKey, HttpServletRequest request, HttpServletResponse response);
}
