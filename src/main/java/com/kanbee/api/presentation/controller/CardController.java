package com.kanbee.api.presentation.controller;

import com.kanbee.api.application.dto.AssignUserDTO;
import com.kanbee.api.application.dto.CardResponseDTO;
import com.kanbee.api.application.service.CardService;
import com.kanbee.api.domain.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    public CardResponseDTO getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CardResponseDTO> getCardsByUser(@PathVariable UUID userId) {
        return cardService.getCardsByUser(userId);
    }

    @GetMapping("/list/{listId}")
    public List<CardResponseDTO> getCardsByList(@PathVariable UUID listId) {
        return cardService.getCardsByList(listId);
    }

    @GetMapping("/board/{boardId}")
    public List<CardResponseDTO> getCardsByBoard(@PathVariable UUID boardId) {
        return cardService.getCardsByBoard(boardId);
    }

    @PostMapping
    public CardResponseDTO createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }

    @PutMapping("/{id}")
    public CardResponseDTO updateCard(@PathVariable UUID id, @RequestBody Card card) {
        return cardService.updateCard(id, card);
    }

    @PatchMapping("/{id}/responsavel")
    public CardResponseDTO assignUser(@PathVariable UUID id, @RequestBody AssignUserDTO request) {
        return cardService.assignUser(id, request);
    }

    @PatchMapping("/{id}/archive")
    public void archiveCard(@PathVariable UUID id) {
        cardService.archiveCard(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable UUID id) {
        cardService.deleteCard(id);
    }
}