package com.example.project_copilot.service;

import com.example.project_copilot.exceptions.BadDataException;
import com.example.project_copilot.exceptions.NotFoundException;
import com.example.project_copilot.model.User;
import com.example.project_copilot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(user -> org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .accountLocked(user.isLocked())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadDataException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void resetPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found: " + username));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User toggleLock(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found: " + username));
        user.setLocked(!user.isLocked());
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) {
            throw new NotFoundException("User not found: " + username);
        }
        userRepository.deleteById(username);
    }
}