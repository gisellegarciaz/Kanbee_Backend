package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    // Cards ativos de um board (via column → board)
    List<Card> findByColumnBoardIdAndArchivedAtIsNull(UUID boardId);

    // Cards ativos atribuídos a um usuário
    List<Card> findByAssignedToIdAndArchivedAtIsNull(UUID userId);

    // Cards de uma coluna
    List<Card> findByColumnIdAndArchivedAtIsNull(UUID columnId);

    // Verifica se existe card na coluna
    boolean existsByColumnIdAndArchivedAtIsNull(UUID columnId);
}