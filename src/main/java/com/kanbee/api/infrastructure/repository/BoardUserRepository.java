package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.BoardUser;
import com.kanbee.api.domain.enums.BoardRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardUserRepository extends JpaRepository<BoardUser, UUID> {

    // Buscar membro específico (user dentro de um board)
    Optional<BoardUser> findByBoardIdAndUserId(UUID boardId, UUID userId);

    // Listar todos os membros de um board
    List<BoardUser> findByBoardId(UUID boardId);

    // Listar todos os boards de um usuário
    List<BoardUser> findByUserId(UUID userId);

    // Verifica se usuário pertence ao board
    boolean existsByBoardIdAndUserId(UUID boardId, UUID userId);

    // Verifica se usuário tem role específica no board
    boolean existsByBoardIdAndUserIdAndRole(UUID boardId, UUID userId, BoardRole role);

    // Buscar membros por role dentro de um board
    List<BoardUser> findByBoardIdAndRole(UUID boardId, BoardRole role);

    // Buscar membership por id garantindo pertencimento ao board
    Optional<BoardUser> findByIdAndBoardId(UUID id, UUID boardId);
}