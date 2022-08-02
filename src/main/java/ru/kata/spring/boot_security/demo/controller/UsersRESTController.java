package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersRESTController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepo;

    @Autowired
    public UsersRESTController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/admin/users/{id}")
    public User returnOne(@PathVariable Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @PostMapping("admin/users/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PutMapping("admin/users")
    public ResponseEntity<User> updateUser(@RequestBody User u) {
        String password = passwordEncoder.encode(u.getPassword());
        u.setPassword(password);
        return ResponseEntity.ok(userRepo.save(u));
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        boolean deleted = userRepo.existsById(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

}
