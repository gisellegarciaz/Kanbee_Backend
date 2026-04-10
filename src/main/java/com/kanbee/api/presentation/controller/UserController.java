package com.kanbee.api.presentation.controller;

import com.kanbee.api.application.dto.CreateUserDTO;
import com.kanbee.api.domain.model.Profile;
import com.kanbee.api.domain.model.User;
import com.kanbee.api.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    // Listar usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Criar user + profile
    @PostMapping
    public User createUser(@RequestBody CreateUserDTO dto) {

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setAvatarUrl(dto.getAvatarUrl());
        profile.setUser(user);

        user.setProfile(profile);

        return userRepository.save(user);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userRepository.deleteById(id);
    }
}