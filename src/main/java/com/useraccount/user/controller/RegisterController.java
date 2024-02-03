package com.useraccount.user.controller;

import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.services.RegisterAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegisterAccountService registerService;
    @GetMapping("/greet")
    public String greet() {
        return "Hello, welcome to the Spring Boot example!";
    }
    
    /**
     *
     * @param registerDTO
     * @return
     */
    @PostMapping("/user/register")
    public ResponseEntity insertUser(
            @RequestBody final UserRegisterRequest registerDTO) {

        AccountRegister save = registerService.save(registerDTO);
        if (save != null) {
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.badRequest().body("failed");
    }

    @GetMapping("user/sign")
    public ResponseEntity signIn(
            @RequestParam(name = "email") final String email,
            @RequestParam(name = "password") final String password
    ) {
        UserRegisterRequest allAccount = registerService.getAllAccount(password, email);
        if (allAccount != null) {
            return ResponseEntity.ok(allAccount);
        }
        return ResponseEntity.badRequest().body("failed");
    }
}
