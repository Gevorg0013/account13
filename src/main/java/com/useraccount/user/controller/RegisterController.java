package com.useraccount.user.controller;

import com.useraccount.user.data.AccountRegister;
import com.useraccount.user.dto.UserRegisterDTO;
import com.useraccount.user.services.RegisterAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterAccountService registerService;
    @GetMapping("/greet")
    public String greet() {
        return "Hello, welcome to the Spring Boot example!";
    }
    
    /**
     *
     * @param registerDTO
     */
    @PostMapping("/user")
    public void insertUser(
            @RequestBody final UserRegisterDTO registerDTO) {
        
        AccountRegister acc = new AccountRegister();
        acc.setPassword(registerDTO.getPassword());
        acc.setUsername(registerDTO.getUsername());
        registerService.save(acc);
    }
}
