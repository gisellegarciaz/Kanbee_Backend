package com.kanbee.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_assignment_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardAssignmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Card card;

    @ManyToOne
    private User assignedTo;

    @CreationTimestamp
    private LocalDateTime assignedAt;

    private LocalDateTime unassignedAt;
}
