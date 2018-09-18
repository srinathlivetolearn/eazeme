package com.yellp.repository;

import com.yellp.dao.ApiKeyRegistryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRegistryRepository extends JpaRepository<ApiKeyRegistryDao,Long> {
    ApiKeyRegistryDao findByApiKey(String apiKey);
}
