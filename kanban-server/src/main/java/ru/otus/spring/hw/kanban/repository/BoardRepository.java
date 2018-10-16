package ru.otus.spring.hw.kanban.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.kanban.domain.Board;

public interface BoardRepository extends ReactiveMongoRepository<Board, String> {
    Flux<Board> findBoardsByNameLike(String name);

    Flux<Board> findAll();

    Flux<Board> findByName(String name);
}
