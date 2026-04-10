package com.kanbee.api.presentation.controller;

import com.kanbee.api.application.dto.AnalyticsDTO;
import com.kanbee.api.application.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // libera pro front, evita erro de Cors!!
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    // Analytics de um card específico
    @GetMapping("/card/{cardId}")
    public List<AnalyticsDTO> getCardAnalytics(@PathVariable UUID cardId) {
        return analyticsService.getCardAnalytics(cardId);
    }

    // Analytics de todos os cards de um board
    @GetMapping("/board/{boardId}")
    public List<AnalyticsDTO> getBoardAnalytics(@PathVariable UUID boardId) {
        return analyticsService.getBoardAnalytics(boardId);
    }
}