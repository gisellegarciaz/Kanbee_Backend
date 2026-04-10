package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.CardAssignmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardAssignmentHistoryRepository extends JpaRepository<CardAssignmentHistory, UUID> {

    List<CardAssignmentHistory> findByCardId(UUID cardId);
}