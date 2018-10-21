package ru.otus.spring.hw.kanban;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;
import ru.otus.spring.hw.kanban.repository.BoardRepository;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class BoardsInit implements ApplicationRunner {

    private BoardRepository boardRepository;

    public BoardsInit(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Set<Stage> stages = new LinkedHashSet<>();
        Set<Task> tasks = new LinkedHashSet<>();
        tasks.add(new Task("task 1", "task descr", "me", 1));
        tasks.add(new Task("task 2", "task descr", "me", 1));
        tasks.add(new Task("task 3", "task descr", "me", 1));
        tasks.add(new Task("task 4", "task descr", "me", 1));
        Stage stage1 = new Stage("think", "slow");
        stage1.setTasks(tasks);

        stages.add(stage1);
        stages.add(new Stage("decide", "fast"));
        boardRepository.deleteAll().thenMany(Flux.just(
                new Board("home", stages),
                new Board("work", stages)))
                .flatMap(boardRepository::save)
                .thenMany(boardRepository.findAll())
                .subscribe(System.out::println);

    }
}
