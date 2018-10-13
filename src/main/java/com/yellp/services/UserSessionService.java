package com.yellp.services;

import com.yellp.dao.UserSessionEntity;

import java.util.Optional;

public interface UserSessionService {
    UserSessionEntity createSession(UserSessionEntity userSession);

    Optional<UserSessionEntity> getActiveSessionForUser(String username);

    boolean invalidateSession(String sessionId);
}
