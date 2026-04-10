package com.kanbee.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private Boolean active = true;

//    // Indica coluna inicial (ex: "To Do")
//    private Boolean isDefaultStart = false;
//
//    // Indica coluna final (ex: "Done")
//    private Boolean isDefaultEnd = false;
}