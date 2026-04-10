package com.kanbee.api.infrastructure.repository;

import com.kanbee.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}