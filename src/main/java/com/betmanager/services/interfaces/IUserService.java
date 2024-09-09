package com.betmanager.services.interfaces;

import com.betmanager.models.entities.UserEntity;

public interface IUserService {
    Boolean createUser(UserEntity user);
}
