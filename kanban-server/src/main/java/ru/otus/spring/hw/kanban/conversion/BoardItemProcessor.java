package ru.otus.spring.hw.kanban.conversion;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BoardItemProcessor implements ItemProcessor<Board, ru.otus.spring.hw.kanban.domain.mongo.Board> {

  @Override
  public ru.otus.spring.hw.kanban.domain.mongo.Board process(Board board) throws Exception {
    ru.otus.spring.hw.kanban.domain.mongo.Board mongoBoard = new ru.otus.spring.hw.kanban.domain.mongo.Board();
    mongoBoard.setName(board.getName());

    Set<ru.otus.spring.hw.kanban.domain.mongo.Stage> mongoStages = new HashSet<>();

    Function<Task, ru.otus.spring.hw.kanban.domain.mongo.Task> convert = (task) -> {
      ru.otus.spring.hw.kanban.domain.mongo.Task mongoTask = new ru.otus.spring.hw.kanban.domain.mongo.Task();
      mongoTask.setDescription(task.getDescription());
      mongoTask.setExecutor(task.getExecutor());
      mongoTask.setName(task.getName());
      mongoTask.setPriority(task.getPriority());
      return mongoTask;
    };

    for (Stage stage : board.getStages()) {
      ru.otus.spring.hw.kanban.domain.mongo.Stage mongoStage = new ru.otus.spring.hw.kanban.domain.mongo.Stage();
      mongoStage.setDescription(stage.getDescription());
      mongoStage.setName(stage.getName());
      mongoStage.setTasks(
        stage.getTasks()
          .stream()
          .map(task -> convert.apply(task))
          .collect(Collectors.toSet())
      );
      mongoStages.add(mongoStage);

    }
    mongoBoard.setStages(mongoStages);
    return mongoBoard;


  }
}
