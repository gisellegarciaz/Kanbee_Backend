package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, UUID> {

    List<BoardColumn> findByBoardIdAndActiveTrueOrderByPositionAsc(UUID boardId);

    Optional<BoardColumn> findByIdAndBoardId(UUID id, UUID boardId);

    Optional<BoardColumn> findTopByBoardIdAndActiveTrueOrderByPositionDesc(UUID boardId);
}