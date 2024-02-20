/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.useraccount.user.controller;

import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.repository.RegisterAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author user
 */
@Controller
public class AuthController {

    @Autowired
    private RegisterAccountRepository userRepository;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new AccountRegister());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute AccountRegister user) {
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
