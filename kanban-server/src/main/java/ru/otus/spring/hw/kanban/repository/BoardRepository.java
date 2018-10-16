package ru.otus.spring.hw.kanban.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.kanban.domain.Board;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Integer> {
    List<Board> findBoardsByNameLike(String name);
    List<Board> findAll();

}
