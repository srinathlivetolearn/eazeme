package com.yellp.dao;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "user_session")
public class UserSessionEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String sessionId;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "created",nullable = false)
    private Timestamp created = new Timestamp(Instant.now().toEpochMilli());

    @Column(name = "expires",nullable = false)
    private Timestamp expires = new Timestamp(Instant.now().plus(Duration.ofDays(30)).toEpochMilli());

    @Column(name = "valid",nullable = false)
    private boolean valid = true;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSessionEntity that = (UserSessionEntity) o;
        return Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(created, that.created) &&
                Objects.equals(expires, that.expires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, username, created, expires);
    }

    @Override
    public String toString() {
        return "UserSessionEntity{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", created=" + created +
                ", expires=" + expires +
                ", valid=" + valid +
                '}';
    }
}
