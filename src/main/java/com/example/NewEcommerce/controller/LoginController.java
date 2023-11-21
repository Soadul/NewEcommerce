package com.example.NewEcommerce.controller;

import com.example.NewEcommerce.Repository.RoleRepository;
import com.example.NewEcommerce.Repository.UserRepository;
import com.example.NewEcommerce.model.Role;
import com.example.NewEcommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {



    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerGet() {
        return "register";
    }

   /* @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        try {
            String password = user.getPassword();
            user.setPassword(password);
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).orElseThrow()); // Using orElseThrow to handle optional
            user.setRoles(roles);
            userRepository.save(user);
            request.login(user.getEmail(), password);
            return "redirect:/";
        } catch (ServletException e) {
            return "loginfail";
        }
    }*/

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) {
        String email = user.getEmail();

        // Check if a user with the provided email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            // Handle the case where the email is already registered, for example, redirect to an error page
            return "redirect:/loginfail";
        }

        // The email is not already registered, proceed with user registration
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password)); // Assuming you have a passwordEncoder bean

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).orElseThrow()); // Using orElseThrow to handle optional
        user.setRoles(roles);
        userRepository.save(user);

        // Log in the user after successful registration
        // request.login(email, password);

        // Redirect to the home page
        return "redirect:/shop";
    }

}
