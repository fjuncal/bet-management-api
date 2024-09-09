package com.betmanager.controllers;


import com.betmanager.models.dtos.AuthenticationRequest;
import com.betmanager.models.dtos.AuthenticationResponse;
import com.betmanager.models.entities.UserEntity;
import com.betmanager.security.JwtTokenUtil;
import com.betmanager.services.UserDetailsServiceImpl;
import com.betmanager.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody UserEntity user){
        Boolean success = userService.createUser(user);
        return new ResponseEntity<>(success, HttpStatus.CREATED);

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
