package com.kanbee.api.presentation.controller;

import com.kanbee.api.application.dto.AssignUserDTO;
import com.kanbee.api.application.dto.CardResponseDTO;
import com.kanbee.api.domain.model.Card;
import com.kanbee.api.domain.model.CardAssignmentHistory;
import com.kanbee.api.domain.model.User;
import com.kanbee.api.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CardController {

    private final CardRepository cardRepository;
    private final BoardColumnRepository boardListRepository;
    private final CardHistoryRepository cardHistoryRepository;
    private final UserRepository userRepository;
    private final CardAssignmentHistoryRepository assignmentRepository;

    // Listar cards por board
    @GetMapping("/board/{boardId}")
    public List<CardResponseDTO> getCardsByBoard(@PathVariable UUID boardId) {
        return cardRepository.findByColumnBoardIdAndArchivedAtIsNull(boardId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Listar cards por user
    @GetMapping("/user/{userId}")
    public List<CardResponseDTO> getCardsByUser(@PathVariable UUID userId) {
        return cardRepository.findByAssignedToIdAndArchivedAtIsNull(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Listar cards por coluna
    @GetMapping("/list/{listId}")
    public List<CardResponseDTO> getCardsByList(@PathVariable UUID listId) {
        return cardRepository.findByColumnIdAndArchivedAtIsNull(listId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Buscar card por ID
    @GetMapping("/{id}")
    public CardResponseDTO getCardById(@PathVariable UUID id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        return toDTO(card);
    }

    // Criar card
    @PostMapping
    public CardResponseDTO createCard(@RequestBody Card card) {
        Card saved = cardRepository.save(card);
        return toDTO(saved);
    }

    // Atualizar card
    @PutMapping("/{id}")
    public CardResponseDTO updateCard(@PathVariable UUID id, @RequestBody Card updatedCard) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setTitle(updatedCard.getTitle());
        card.setDescription(updatedCard.getDescription());
        card.setPriority(updatedCard.getPriority());
        card.setDueDate(updatedCard.getDueDate());
        card.setAssignedTo(updatedCard.getAssignedTo());

        Card saved = cardRepository.save(card);

        return toDTO(saved);
    }

    // patch no responsável pela tarefa (card)
    @PatchMapping("/{id}/responsavel")
    public CardResponseDTO assignUser(
            @PathVariable UUID id,
            @RequestBody AssignUserDTO request
    ) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Atualiza responsável
        card.setAssignedTo(user);
        Card updatedCard = cardRepository.save(card);

        // histórico de atribuição
        CardAssignmentHistory history = new CardAssignmentHistory();
        history.setCard(card);
        history.setAssignedTo(user);
        history.setAssignedAt(java.time.LocalDateTime.now());

        assignmentRepository.save(history);

        return toDTO(updatedCard);
    }

    // Soft delete
    @PatchMapping("/{id}/archive")
    public void archiveCard(@PathVariable UUID id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setArchivedAt(java.time.LocalDateTime.now());

        cardRepository.save(card);
    }

    // Delete físico
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable UUID id) {
        cardRepository.deleteById(id);
    }

    // Conversão para DTO
    private CardResponseDTO toDTO(Card card) {

        String userName = null;

        if (card.getAssignedTo() != null && card.getAssignedTo().getProfile() != null) {
            userName = card.getAssignedTo().getProfile().getName();
        }

        return new CardResponseDTO(
                card.getId(),
                card.getTitle(),
                card.getDescription(),
                card.getPriority() != null ? card.getPriority().name() : null,
                card.getDueDate(),
                card.getColumn() != null ? card.getColumn().getTitle() : null,
                userName,
                card.getCreatedAt()
        );
    }
}