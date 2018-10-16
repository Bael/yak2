package ru.otus.spring.hw.kanban;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.repository.BoardRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class BoardsInit implements ApplicationRunner {

    private BoardRepository boardRepository;

    public BoardsInit(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Set<Stage> stages = new HashSet<>();
        stages.add(new Stage("think", "slow"));
        stages.add(new Stage("decide", "fast"));
        boardRepository.deleteAll().thenMany(Flux.just(
                new Board("home", stages),
                new Board("work", stages)))
                .flatMap(boardRepository::save)
                .thenMany(boardRepository.findAll())
                .subscribe(System.out::println);

    }
}
