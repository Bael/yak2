package ru.otus.spring.hw.kanban.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.domain.Board;

public interface BoardRepository extends ReactiveMongoRepository<Board, String> {
    Flux<Board> findBoardsByNameLike(String name);

    Flux<Board> findAll();

    Flux<Board> findByName(String name);

    Mono<Board> findById(String id);

    @Query(value="{ 'id' : ?0 }", fields="{ 'stages' : 1 }")
    Mono<Board> findStagesByBoard(String boardId);
}
