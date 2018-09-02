package ru.otus.spring.hw.kanban.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;

import java.util.List;

public interface StageRepository extends CrudRepository<Stage, Integer> {

//    @Query(value = "SELECT s FROM Stage s WHERE s.board = :boardId")
//    List<Stage> findStagesByBoardId(@Param("boardId") Integer boardId);

    List<Stage> findStagesByBoard(Board board);

    List<Stage> findAll();
}
