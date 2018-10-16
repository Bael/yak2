package ru.otus.spring.hw.kanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "ru.otus.spring.hw.kanban.repository")
public class KanbanApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanbanApplication.class, args);
    }
}
