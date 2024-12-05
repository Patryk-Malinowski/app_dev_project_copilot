
package com.example.project_copilot.controller;

import com.example.project_copilot.model.User;
import com.example.project_copilot.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{username}/password")
    public ResponseEntity<Void> resetPassword(@PathVariable String username, @RequestBody String newPassword) {
        userService.resetPassword(username, newPassword);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}/toggle-lock")
    public ResponseEntity<User> toggleLock(@PathVariable String username) {
        return ResponseEntity.ok(userService.toggleLock(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}