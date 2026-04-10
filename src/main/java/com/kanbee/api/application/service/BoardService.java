package com.kanbee.api.application.service;

import com.kanbee.api.application.exception.ResourceNotFoundException;
import com.kanbee.api.domain.model.Board;
import com.kanbee.api.infrastructure.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(UUID id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public void deleteBoard(UUID id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Board not found");
        }
        boardRepository.deleteById(id);
    }
}