package com.yellp.services.impl;

import com.yellp.dao.UserSessionEntity;
import com.yellp.repository.UserSessionRepository;
import com.yellp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserSessionServiceImpl implements UserSessionService {
    @Autowired
    private UserSessionRepository sessionRepository;

    @Override
    @Transactional
    public UserSessionEntity createSession(UserSessionEntity userSession) {
        return sessionRepository.save(userSession);
    }

    @Override
    public Optional<UserSessionEntity> getActiveSessionForUser(String username) {
        return sessionRepository.findOptionalByUsernameAndValid(username,true);
                //.filter(session -> Instant.now().isBefore(Instant.ofEpochMilli(session.getExpires().getTime())));
    }

    @Override
    @Transactional
    public boolean invalidateSession(String sessionId) {
        return sessionRepository.updateValidById(sessionId,false) > 0;
    }
}
