package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;


@Controller
public class UsersController {
    private final UserService userService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserRepository userRepo, UserService userService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    public String goToUserPage(Authentication authentication, Model model) {
        model.addAttribute("user", userService.getUserById(userService.findByUserEmail(authentication.getName()).getId()));
        return "user";
    }

    @GetMapping("/admin")
    public String goToAdminPage(Authentication authentication, Model model) {
        model.addAttribute("user", userService.getUserById(userService.findByUserEmail(authentication.getName()).getId()));
        List<Role> listRoles = userService.listRolesForAUser();
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("listRoles", listRoles);
        return "admin";
    }
}