package ru.otus.spring.hw.kanban.service;

import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.dto.BoardDTO;

import java.util.List;


public interface BoardService {

    List<BoardDTO> findAll();

    BoardDTO find(int id);

    BoardDTO create(BoardDTO newBoard);

    BoardDTO update(BoardDTO boardToUpdate);

    void deleteById(int id);

    Board delete(Board payload);
}
