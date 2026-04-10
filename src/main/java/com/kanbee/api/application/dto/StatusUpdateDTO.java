package com.kanbee.api.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StatusUpdateDTO {
    private UUID targetListId;
}

//@NotNull(message = "O novo status é obrigatório.")
//private StatusTarefa status;
//
//@NotNull(message = "A nova posição é obrigatória.")
//private Integer position;
//