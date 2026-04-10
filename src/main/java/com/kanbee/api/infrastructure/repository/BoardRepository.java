package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.Board;
import com.kanbee.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}