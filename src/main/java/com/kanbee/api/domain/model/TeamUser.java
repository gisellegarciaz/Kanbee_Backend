package com.kanbee.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Team team;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;
}