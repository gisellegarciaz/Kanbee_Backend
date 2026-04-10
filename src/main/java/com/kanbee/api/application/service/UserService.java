package com.kanbee.api.application.service;

import com.kanbee.api.application.dto.CreateUserDTO;
import com.kanbee.api.application.exception.ResourceNotFoundException;
import com.kanbee.api.domain.exception.BusinessException;
import com.kanbee.api.domain.model.User;
import com.kanbee.api.infrastructure.repository.UserRepository;
import com.kanbee.api.infrastructure.repository.BoardUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardUserRepository boardUserRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    public User createUser(CreateUserDTO dto) {

        // email não pode duplicar
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já está em uso.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        user.createProfile(dto.getName());

        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public List<User> getUsersByBoard(UUID boardId) {

        List<User> users = boardUserRepository.findUsersByBoardId(boardId);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found for this board");
        }
        return users;
    }
}