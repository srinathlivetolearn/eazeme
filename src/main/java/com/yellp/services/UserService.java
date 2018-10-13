package com.yellp.services;

import com.yellp.dao.UserEntity;

public interface UserService {
    UserEntity registerUser(UserEntity user);
}
