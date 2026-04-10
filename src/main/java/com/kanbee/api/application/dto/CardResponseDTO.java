package com.kanbee.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDTO {

    // o get não vai ter nenhuma info sensivel do user
    // evita o loop de user -> profile -> user -> ...

    private UUID id;

    private String title;

    private String description;

    private String priority;

    private LocalDateTime dueDate;

    private String listName;

    private String assignedUserName;

    private LocalDateTime createdAt;
}