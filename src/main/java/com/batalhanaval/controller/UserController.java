package com.batalhanaval.controller;

import com.batalhanaval.entity.User;
import com.batalhanaval.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user = this.userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.userService.getUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId) {
        user = this.userService.updateUser(userId, user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }
}
