package ru.otus.spring.hw.kanban.service;

import reactor.core.publisher.Flux;
import ru.otus.spring.hw.kanban.domain.Board;


public interface BoardService {

    Flux<Board> findAll();

    /*
    BoardDTO find(int id);

    BoardDTO create(BoardDTO newBoard);

    BoardDTO update(BoardDTO boardToUpdate);

    void deleteById(int id); */
}
