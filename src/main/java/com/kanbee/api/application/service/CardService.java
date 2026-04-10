package com.kanbee.api.application.service;

import com.kanbee.api.application.dto.AssignUserDTO;
import com.kanbee.api.application.dto.CardResponseDTO;
import com.kanbee.api.application.exception.ResourceNotFoundException;
import com.kanbee.api.domain.model.Card;
import com.kanbee.api.domain.model.CardAssignmentHistory;
import com.kanbee.api.domain.model.User;
import com.kanbee.api.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardAssignmentHistoryRepository assignmentRepository;

    public List<CardResponseDTO> getCardsByBoard(UUID boardId) {
        return cardRepository.findByColumnBoardIdAndArchivedAtIsNull(boardId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CardResponseDTO> getCardsByUser(UUID userId) {
        return cardRepository.findByAssignedToIdAndArchivedAtIsNull(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CardResponseDTO> getCardsByList(UUID listId) {
        return cardRepository.findByColumnIdAndArchivedAtIsNull(listId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CardResponseDTO getCardById(UUID id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        return toDTO(card);
    }

    public CardResponseDTO createCard(Card card) {
        return toDTO(cardRepository.save(card));
    }

    public CardResponseDTO updateCard(UUID id, Card updatedCard) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        card.setTitle(updatedCard.getTitle());
        card.setDescription(updatedCard.getDescription());
        card.setPriority(updatedCard.getPriority());
        card.setDueDate(updatedCard.getDueDate());
        card.setAssignedTo(updatedCard.getAssignedTo());

        return toDTO(cardRepository.save(card));
    }

    public CardResponseDTO assignUser(UUID cardId, AssignUserDTO request) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        card.setAssignedTo(user);
        Card updatedCard = cardRepository.save(card);

        CardAssignmentHistory history = new CardAssignmentHistory();
        history.setCard(card);
        history.setAssignedTo(user);
        history.setAssignedAt(LocalDateTime.now());

        assignmentRepository.save(history);

        return toDTO(updatedCard);
    }

    public void archiveCard(UUID id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        card.setArchivedAt(LocalDateTime.now());
        cardRepository.save(card);
    }

    public void deleteCard(UUID id) {
        if (!cardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Card not found");
        }
        cardRepository.deleteById(id);
    }

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