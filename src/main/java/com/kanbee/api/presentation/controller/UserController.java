package com.kanbee.api.presentation.controller;

import com.kanbee.api.application.dto.CreateUserDTO;
import com.kanbee.api.application.service.UserService;
import com.kanbee.api.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // Listar todos os usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Buscar usuários por board
    @GetMapping("/by-board")
    public List<User> getUsersByBoard(@RequestParam UUID boardId) {
        return userService.getUsersByBoard(boardId);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    // Criar usuário + profile
    @PostMapping
    public User createUser(@RequestBody CreateUserDTO dto) {
        return userService.createUser(dto);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}