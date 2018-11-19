package ru.otus.spring.hw.kanban.conversion;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;
import ru.otus.spring.hw.kanban.repository.StageRepository;
import ru.otus.spring.hw.kanban.repository.TaskRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BoardItemProcessor implements ItemProcessor<Board, ru.otus.spring.hw.kanban.domain.mongo.Board>  {


  @Autowired
  StageRepository stageRepository;

  @Autowired
  TaskRepository taskRepository;

  @Override
  public ru.otus.spring.hw.kanban.domain.mongo.Board process(Board board) throws Exception {
    ru.otus.spring.hw.kanban.domain.mongo.Board mongoBoard = new ru.otus.spring.hw.kanban.domain.mongo.Board();
    mongoBoard.setName(board.getName());

    List<Stage> stages = stageRepository.findStagesByBoard(board);
    Set<ru.otus.spring.hw.kanban.domain.mongo.Stage> mongoStages = new HashSet<>();

    Function<Task, ru.otus.spring.hw.kanban.domain.mongo.Task> convert = (task) -> {
      ru.otus.spring.hw.kanban.domain.mongo.Task mongoTask = new ru.otus.spring.hw.kanban.domain.mongo.Task();
      mongoTask.setDescription(task.getDescription());
      mongoTask.setExecutor(task.getExecutor());
      mongoTask.setName(task.getName());
      mongoTask.setPriority(task.getPriority());
      return  mongoTask;
    };

    for (Stage stage : stages) {
      ru.otus.spring.hw.kanban.domain.mongo.Stage mongoStage = new ru.otus.spring.hw.kanban.domain.mongo.Stage();
      mongoStage.setDescription(stage.getDescription());
      mongoStage.setName(stage.getName());


      mongoStage.setTasks(
        taskRepository.findAllByStage(stage)
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
