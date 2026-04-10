package com.kanbee.api.presentation.controller;

import com.kanbee.api.application.service.BoardColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardColumnService service;

    @PatchMapping("/{columnId}/reorder")
    public void reorder(
            @PathVariable UUID columnId,
            @RequestParam UUID boardId,
            @RequestParam Integer position
    ) {
        service.reorderColumn(columnId, boardId, position);
    }
}