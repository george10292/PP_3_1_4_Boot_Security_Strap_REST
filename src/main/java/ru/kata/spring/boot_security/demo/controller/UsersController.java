package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/admin/{id}")
    public String userById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.userByid(id));
        return "user-info";
    }

    @GetMapping("/admin")
    public String findAll(Model model){
        List<User> users = userService.usersAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(User user){
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(User user, @RequestParam(value = "role") String[] roles){
        user.setRoles(userService.getRoles(roles));
        userService.userAdd(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.userDelete(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.userByid(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(User user, @RequestParam(value = "role") String[] roles){
        user.setRoles(userService.getRoles(roles));
        userService.userAdd(user);
        return "redirect:/admin";
    }
    @GetMapping("/user-registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "user-registration";
    }
    @PostMapping("/user-registration")
    public String performRegistration (@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles) {
        user.setRoles(userService.getRoles(roles));
        userService.userAdd(user);
        return "redirect:/";
    }
}