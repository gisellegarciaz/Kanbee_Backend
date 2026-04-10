package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.CardHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardHistoryRepository extends JpaRepository<CardHistory, UUID> {

    List<CardHistory> findByCardIdOrderByChangedAt(UUID cardId);
}