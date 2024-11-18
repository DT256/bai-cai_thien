package com.example.controller;

import com.example.model.LoginResponse;
import com.example.model.LoginUserModel;
import com.example.model.RegisterUserModel;
import com.example.service.impl.AuthenticationService;
import com.example.service.impl.JwtService;
import com.example.entity.Users;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<Users> register(@RequestBody RegisterUserModel registerUser) {
        Users registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
        Users authenticatedUser = authenticationService.authenticate(loginUser);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpires(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}

