package ru.otus.spring.hw.kanban;

//import org.h2.tools.Console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Task;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
import ru.otus.spring.hw.kanban.repository.TaskRepository;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.otus.spring.hw.kanban.repository"})
public class KanbanApplication {

    public static void main(String[] args) {

        //SpringApplication.run(KanbanApplication.class, args);

//        Console.main(args);
        ConfigurableApplicationContext ctx = SpringApplication.run(KanbanApplication.class, args);
        BoardRepository boardRepository = ctx.getBean(BoardRepository.class);

        Board board = new Board("new board");

        boardRepository.save(board);

        TaskRepository taskRepository = ctx.getBean(TaskRepository.class);

        Task task = new Task("new task", "description", "me", 5);

        taskRepository.save(task);


        System.out.println(board.getId());
    }
}
