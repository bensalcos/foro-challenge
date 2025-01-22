package com.challenge.foro.controller;

import com.challenge.foro.dto.LoginDTO;
import com.challenge.foro.dto.RegistroDTO;
import com.challenge.foro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegistroDTO registroDTO) {
        return authService.register(registroDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}