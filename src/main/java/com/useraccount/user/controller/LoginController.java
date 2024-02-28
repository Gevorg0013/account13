package com.useraccount.user.controller;

import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.dto.UserRegisterResponse;
import com.useraccount.user.repository.RegisterAccountRepository;
import com.useraccount.user.services.RegisterAccountService;
import com.useraccount.user.util.EmailSenderService;
import com.useraccount.user.util.JwtTokenUtil;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/newpage")
    public String showNewPage(@RequestParam Long id, Model model) {
        // Assuming registerService.getUserById(id) returns an Optional<UserRegisterResponse>
        Optional<UserRegisterResponse> userById = registerService.getUserById(id);

        // Check if the user is present in the optional
        userById.ifPresent(user -> model.addAttribute("user", user));

        return "newpage"; // Return the name of the Thymeleaf template
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "email") final String email,
            @RequestParam(name = "password") final String password,
            RedirectAttributes redirectAttributes
    ) {

        UserRegisterResponse accountByEmail = registerService.getAllAccount(password, email);
        String authToken = utilClass.generateToken(accountByEmail.getFirstName());

        UserRegisterResponse result = modelMapper.map(accountByEmail, UserRegisterResponse.class);

        if (result != null) {
            result.setToken(authToken);
            redirectAttributes.addAttribute("id", result.getId());

            return "redirect:/newpage";

        }
        return "redirect:/login?error";
    }
}
