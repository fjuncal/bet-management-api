package com.betmanager.interfaces.rest.controllers;


import com.betmanager.models.dtos.ApiResponse;
import com.betmanager.models.dtos.AuthenticationRequest;
import com.betmanager.models.dtos.AuthenticationResponse;
import com.betmanager.models.entities.UserEntity;
import com.betmanager.security.JwtTokenUtil;
import com.betmanager.services.UserDetailsServiceImpl;
import com.betmanager.services.UserServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public ResponseEntity<ApiResponse<Boolean>> registerUser(@RequestBody UserEntity user) {
        Boolean success = userService.createUser(user);
        ApiResponse<Boolean> response = new ApiResponse<>(
                "success",
                success,
                "User registered successfully"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @RateLimiter(name = "loginRateLimiter", fallbackMethod = "rateLimitFallback")
    public ResponseEntity<ApiResponse<AuthenticationResponse>>  createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authResponse = new AuthenticationResponse(jwt);

        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(
                "success",
                authResponse,
                "Authentication successful"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public  ResponseEntity<ApiResponse<AuthenticationResponse>> rateLimitFallback(AuthenticationRequest authenticationRequest, Throwable t) {
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(
                "error",
                null,
                "Too many requests, please try again later."
        );
        return ResponseEntity.status(429).body(response);
    }
}
