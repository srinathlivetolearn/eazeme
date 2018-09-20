package com.yellp.services.impl;

import com.yellp.dao.ApiKeyRegistryDao;
import com.yellp.repository.ApiKeyRegistryRepository;
import com.yellp.services.ApiKeyValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.yellp.utils.Constants.API_KEY_USER_ID;

@Service
public class ApiKeyValidationServiceImpl implements ApiKeyValidationService {
    @Autowired
    private ApiKeyRegistryRepository repository;

    @Override
    public boolean validateApiKey(String apiKey, HttpServletRequest request, HttpServletResponse response) {
        ApiKeyRegistryDao keyInfo = repository.findByApiKey(apiKey);
        if(keyInfo != null && keyInfo.isValid()) {
            HttpSession session = request.getSession(false);
            if(session != null) {
                session.setAttribute(API_KEY_USER_ID.value(),keyInfo.getUserId());
            }
            return true;
        }
        return false;
    }
}
