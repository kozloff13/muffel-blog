package com.technoir.levelup.controller;

import com.technoir.levelup.model.User;
import com.technoir.levelup.repository.UserRepository;
import com.technoir.levelup.security.jwt.JwtTokenProvider;
import com.technoir.levelup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class RegistrationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<String> registration(@RequestBody User user) {
        if (user.getUsername() == null
                || user.getEmail() == null
                || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Не все поля заполнены");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(406).body("Пользователь с таким email уже существует");
        }
        userService.register(user);
        return ResponseEntity.ok(user.getUsername());
    }
}