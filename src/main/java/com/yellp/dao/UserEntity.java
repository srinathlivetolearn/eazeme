package com.yellp.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table (name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "username",unique = true,nullable = false)
    private String username;

    @Column(name = "fname",nullable = false)
    private String firstName;

    @Column(name = "lname",nullable = false)
    private String lastName;

    @Column(name = "phone",unique = true,nullable = false)
    private String phoneNumber;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "created",nullable = false)
    private Timestamp created = Timestamp.from(Instant.now());

    @Column(name = "active",nullable = false)
    private boolean isActive = true;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated() {
        return created;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Retuns a safe copy of UserEntity with secure values such as password and active flags masked.
     * @return UserEntity with masked values which can be safely returned as a response.
     */
    public UserEntity withExposableMembers() {
        UserEntity entity = new UserEntity();

        entity.setUserId(this.userId);
        entity.setUsername(this.username);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setPhoneNumber(this.phoneNumber);
        entity.setPassword("[PROTECTED_VALUE]");

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (!username.equals(that.username)) return false;
        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        return phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", created=" + created +
                '}';
    }
}
