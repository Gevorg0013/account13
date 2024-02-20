package com.useraccount.user.controller;

import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.dto.UserRegisterResponse;
import com.useraccount.user.repository.RegisterAccountRepository;
import com.useraccount.user.services.RegisterAccountService;
import com.useraccount.user.util.EmailSenderService;
import com.useraccount.user.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// author gevorga
@Controller
public class LoginController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private JwtTokenUtil utilClass;

    @Autowired
    private RegisterAccountRepository repo;

    @Autowired
    private RegisterAccountService registerService;

    @Autowired
    private EmailSenderService emailService;

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestParam(name = "email") final String email,
            @RequestParam(name = "password") final String password
    ) {

        UserRegisterRequest accountByEmail = registerService.getAllAccount(password, email);
        String authToken = utilClass.generateToken(accountByEmail.getFirstName());

        UserRegisterResponse result = modelMapper.map(accountByEmail, UserRegisterResponse.class);

        if (result != null) {
            result.setToken(authToken);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("save operation failed");
    }
}
