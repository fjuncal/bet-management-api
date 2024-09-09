package com.betmanager.controllers;


import com.betmanager.exception.NoUserFoundException;
import com.betmanager.models.dtos.AuthenticationRequest;
import com.betmanager.models.dtos.AuthenticationResponse;
import com.betmanager.models.dtos.ErrorResponse;
import com.betmanager.models.entities.UserEntity;
import com.betmanager.repositories.UserRepository;
import com.betmanager.security.JwtTokenUtil;
import com.betmanager.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody UserEntity user){

        Optional<UserEntity> userByUsername = userRepository.findByUsername(user.getUsername());

        if(userByUsername.isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (AuthenticationException e){
            log.error(HttpStatus.NOT_FOUND + " Invalid username or password " + e.getMessage());
            return new ResponseEntity<>(new AuthenticationResponse( "Invalid username or password"), HttpStatus.NOT_FOUND);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }
}
