package com.useraccount.user.controller;

import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.dto.UserRegisterResponse;
import com.useraccount.user.services.RegisterAccountService;
import com.useraccount.user.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

    ModelMapper modelMapper = new ModelMapper();
    
    
    @Autowired
    private JwtTokenUtil utilClass;

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
    @PostMapping("/user/signUp")
    public ResponseEntity signUp(
            @RequestBody final UserRegisterRequest registerDTO) {

        if(registerService.emailVerification(registerDTO.getEmail())) {
             return ResponseEntity.badRequest().body("email isn't unique");
        }
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

        UserRegisterRequest accountByEmail = registerService.getAllAccount(password, email);
        String authToken = utilClass.generateToken(accountByEmail.getFirstName());

        UserRegisterResponse result = modelMapper.map(accountByEmail, UserRegisterResponse.class);

        if (result != null) {
            result.setToken(authToken);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("save operation failed");
    }
    
    @GetMapping("token/test")
    public ResponseEntity<String> testToken(@RequestParam String token) {
        boolean validateToken = utilClass.validateToken(token);
        if(validateToken == true) {
            return ResponseEntity.ok().body("token verification passed succsessfully ");
            
        } else {
            return ResponseEntity.badRequest().body("verification failed");
        }
    }
}
