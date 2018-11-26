package ru.otus.spring.hw.kanban.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.otus.spring.courseproject.yag.dto.TaskDTO;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
import ru.otus.spring.hw.kanban.repository.StageRepository;
import ru.otus.spring.hw.kanban.repository.TaskRepository;

import javax.transaction.Transactional;

@Component
public class TaskReciever {

  @Autowired
  TaskRepository taskRepository;

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  StageRepository stageRepository;


  @JmsListener(destination = "jms-tasks-queue")
  @SendTo("jms.task-reply-queue")
  @Transactional
  public Message<String> processTask(TaskDTO task) {

    System.out.println("processTask called " + task);
    Board board = boardRepository.findAll().get(0);
    Stage stage = stageRepository.findStagesByBoard(board).get(0);

    // create task and return any id
    Task kanbanTask = new Task();
    kanbanTask.setName(task.getDescription());
    kanbanTask.setStage(stage);

    taskRepository.save(kanbanTask);


    return MessageBuilder
      .withPayload("PROCCESSED")
      .setHeader("ID", task.getId())
      .build();
  }
}
