package ru.otus.spring.hw.kanban.repository;

import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;

import java.util.List;

public interface StageRepository {

    List<Stage> findStagesByBoard(Board board);

    List<Stage> findAll();
}
