package com.yellp.repository;

import com.yellp.dao.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity,String> {

    Optional<UserSessionEntity> findOptionalByUsernameAndValid(String username,boolean valid);

    @Modifying
    @Query("update UserSessionEntity u set u.valid = ?2 where u.sessionId = ?1")
    int updateValidById(String sessionId,boolean valid);
}
