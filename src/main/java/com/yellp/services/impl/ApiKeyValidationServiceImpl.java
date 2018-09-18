package com.yellp.services.impl;

import com.yellp.dao.ApiKeyRegistryDao;
import com.yellp.repository.ApiKeyRegistryRepository;
import com.yellp.services.ApiKeyValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyValidationServiceImpl implements ApiKeyValidationService {
    @Autowired
    private ApiKeyRegistryRepository repository;

    @Override
    public boolean validateApiKey(String apiKey) {
        ApiKeyRegistryDao keyInfo = repository.findByApiKey(apiKey);
        return null != keyInfo && keyInfo.isValid();
    }
}
