package com.kanbee.api.application.service;

import com.kanbee.api.domain.model.Board;
import com.kanbee.api.domain.model.BoardColumn;
import com.kanbee.api.infrastructure.repository.BoardColumnRepository;
import com.kanbee.api.infrastructure.repository.BoardRepository;
import com.kanbee.api.infrastructure.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private final BoardColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    // Criar nova coluna
    @Transactional
    public BoardColumn createColumn(UUID boardId, String title) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Integer newPosition = columnRepository
                .findTopByBoardIdAndActiveTrueOrderByPositionDesc(boardId)
                .map(col -> col.getPosition() + 1)
                .orElse(0);

        BoardColumn column = new BoardColumn();
        column.setTitle(title);
        column.setBoard(board);
        column.setPosition(newPosition);
        column.setActive(true);

        return columnRepository.save(column);
    }

    // Listar colunas do board (ordenadas)
    public List<BoardColumn> getColumnsByBoard(UUID boardId) {
        return columnRepository.findByBoardIdAndActiveTrueOrderByPositionAsc(boardId);
    }

    // Soft delete de coluna
    @Transactional
    public void deleteColumn(UUID columnId, UUID boardId) {

        BoardColumn column = columnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        // NÃO PODE DELETAR A COLUNA SE TIVER TAREFAS NELA
        boolean hasTasks = cardRepository.existsByColumnIdAndArchivedAtIsNull(columnId);

        if (hasTasks) {
            throw new IllegalStateException("Cannot delete column with existing cards");
        }

        List<BoardColumn> columns = columnRepository
                .findByBoardIdAndActiveTrueOrderByPositionAsc(boardId);

        // remove a coluna da lista antes de reorganizar
        columns.removeIf(col -> col.getId().equals(columnId));

        // Reorganiza posições
        int pos = 0;
        for (BoardColumn col : columns) {
            col.setPosition(pos++);
        }

        column.setActive(false);

        columnRepository.saveAll(columns);
        columnRepository.save(column);
    }

    // Reorder (drag and drop)
    @Transactional
    public void reorderColumn(UUID columnId, UUID boardId, Integer newPosition) {

        List<BoardColumn> columns = columnRepository
                .findByBoardIdAndActiveTrueOrderByPositionAsc(boardId);

        if (newPosition < 0 || newPosition >= columns.size()) {
            throw new IllegalArgumentException("Invalid position");
        }

        BoardColumn column = columnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        Integer oldPosition = column.getPosition();

        if (oldPosition.equals(newPosition)) return;

        if (newPosition < oldPosition) {
            // Subindo
            columns.stream()
                    .filter(c -> !c.getId().equals(columnId))
                    .filter(c -> c.getPosition() >= newPosition && c.getPosition() < oldPosition)
                    .forEach(c -> c.setPosition(c.getPosition() + 1));

        } else {
            // Descendo
            columns.stream()
                    .filter(c -> !c.getId().equals(columnId))
                    .filter(c -> c.getPosition() <= newPosition && c.getPosition() > oldPosition)
                    .forEach(c -> c.setPosition(c.getPosition() - 1));
        }

        column.setPosition(newPosition);

        columnRepository.saveAll(columns);
        columnRepository.save(column);
    }
}