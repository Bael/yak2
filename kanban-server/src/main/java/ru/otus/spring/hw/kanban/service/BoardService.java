package ru.otus.spring.hw.kanban.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.dto.BoardDTO;


public interface BoardService {

    Flux<Board> findAll();

    Mono<Board> find(String id);

    Mono<Board> create(BoardDTO newBoard);

    Mono<Board> update(BoardDTO boardToUpdate);

    Mono<Void> deleteAll();

    void deleteById(String id);
}
