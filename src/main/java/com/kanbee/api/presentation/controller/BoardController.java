package com.kanbee.api.presentation.controller;

import com.kanbee.api.domain.model.Board;
import com.kanbee.api.infrastructure.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping
    public List<Board> listAll() {
        return boardRepository.findAll();
    }

    @PostMapping
    public Board create(@RequestBody Board board) {
        return boardRepository.save(board);
    }
}