package com.kanbee.api.domain.repository;

import com.kanbee.api.domain.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}