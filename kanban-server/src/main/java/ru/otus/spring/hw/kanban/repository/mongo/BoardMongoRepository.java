package ru.otus.spring.hw.kanban.repository.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw.kanban.domain.mongo.Board;

import java.util.List;

public interface BoardMongoRepository extends MongoRepository<Board, String> {
  List<Board> findBoardsByNameLike(String name);

  List<Board> findAll();


}
