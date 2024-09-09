package com.betmanager.services;

import com.betmanager.exception.UserAlreadyExist;
import com.betmanager.models.entities.UserEntity;
import com.betmanager.repositories.UserRepository;
import com.betmanager.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean createUser(UserEntity user) {

        //vejo se o username ja existe
        Optional<UserEntity> userByUsername = userRepository.findByUsername(user.getUsername());

        if(userByUsername.isPresent()){
            throw new UserAlreadyExist("User Already Exist with username: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return Boolean.TRUE;
    }
}
