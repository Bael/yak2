package ru.otus.spring.hw.kanban.service;

import ru.otus.spring.hw.kanban.dto.TaskDTO;

import java.util.List;

public interface TaskService {

  List<TaskDTO> findAll();

  List<TaskDTO> findAllByStage(int stageId);

  TaskDTO find(int id);

  TaskDTO create(TaskDTO newTaskDTO);

  TaskDTO update(TaskDTO taskToUpdate);

  TaskDTO moveToNextStage(TaskDTO taskToUpdate);

  void deleteById(int id);

}
