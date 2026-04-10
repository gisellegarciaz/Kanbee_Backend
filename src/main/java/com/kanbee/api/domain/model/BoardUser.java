package com.kanbee.api.domain.model;

import com.kanbee.api.domain.enums.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "board_users")
public class BoardUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;
}
