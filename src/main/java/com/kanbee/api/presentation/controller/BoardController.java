package com.kanbee.api.presentation.controller;

import com.kanbee.api.domain.model.Board;
import com.kanbee.api.application.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<Board> listAll() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public Board getById(@PathVariable UUID id) {
        return boardService.getBoardById(id);
    }

    @PostMapping
    public Board create(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        boardService.deleteBoard(id);
    }
}