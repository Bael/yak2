package ru.otus.spring.hw.kanban.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findAllByStage(Stage stage);
}
