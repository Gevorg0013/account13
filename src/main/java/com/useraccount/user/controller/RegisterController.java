package com.useraccount.user.controller;

import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.dto.UserRegisterResponse;
import com.useraccount.user.services.RegisterAccountService;
import com.useraccount.user.util.JwtTokenUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/user/list")
    public ResponseEntity getuserList(@RequestParam final String token) {
        boolean validateToken = utilClass.validateToken(token);
        if (!validateToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token is invalid!");
        }
        List<UserRegisterResponse> allUsers = registerService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/user/by/id")
    public ResponseEntity getUserByid(
            @RequestParam(name = "token") final String token,
            @RequestParam(name = "userid") final Long userId
    ) {
        boolean validateToken = utilClass.validateToken(token);
        if (!validateToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token is invalid!");
        }
        Optional<UserRegisterResponse> userById = registerService.getUserById(userId);
        if(userById.isPresent()) {
            return ResponseEntity.ok(userById.get());
        } 
        return ResponseEntity.badRequest().body("can't find user via this id!");

    }

    @DeleteMapping("remove/user/by/id")
    public ResponseEntity deleteUserById(
            @RequestParam(name = "userId") final Long userid,
            @RequestParam(name = "token") String token
    ) {
        boolean validateToken = utilClass.validateToken(token);
        if (!validateToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token is invalid!");
        } else {
            boolean deleteById = registerService.deleteById(userid);
            if (deleteById) {
                return ResponseEntity.ok("deletion passed successfully");
            } else {
                return ResponseEntity.ok("deletion failed");
            }
        }
    } 
    
    
}
