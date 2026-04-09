package com.kanbee.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "card_labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Card card;

    @ManyToOne
    private Label label;
}