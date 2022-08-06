package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersRESTController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserService userService;

    @Autowired
    public UsersRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users/{id}")
    public User returnOne(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("admin/users/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("admin/users")
    public ResponseEntity<User> updateUser(@RequestBody User u) {
        String password = passwordEncoder.encode(u.getPassword());
        u.setPassword(password);
        return ResponseEntity.ok(userService.saveUser(u));
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(userService.getUserById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> showAllUsers() {
        final List<User> users = userService.findAll();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
