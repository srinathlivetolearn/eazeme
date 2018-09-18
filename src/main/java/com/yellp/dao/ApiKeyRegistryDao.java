package com.yellp.dao;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

import static java.time.Instant.now;

@Entity
@Table(name = "APIKEYREGISTRY")
public class ApiKeyRegistryDao {
    @Id
    @Column(name = "apikey",nullable = false,unique = true)
    private String apiKey;

    @Column(name = "userid",nullable = false)
    private String userId;

    @Column(name = "generatedon",nullable = false)
    private Timestamp generatedOn = new Timestamp(now().toEpochMilli());

    @Column(name = "valid",nullable = false)
    private Boolean valid = false;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(Timestamp generatedOn) {
        this.generatedOn = generatedOn;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
