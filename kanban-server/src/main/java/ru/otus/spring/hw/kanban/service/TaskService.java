package ru.otus.spring.hw.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;
import ru.otus.spring.hw.kanban.dto.TaskDTO;
import ru.otus.spring.hw.kanban.exceptions.StageNotFoundException;
import ru.otus.spring.hw.kanban.exceptions.TaskNotFoundException;
import ru.otus.spring.hw.kanban.repository.StageRepository;
import ru.otus.spring.hw.kanban.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {


    @Autowired
    StageRepository stageRepository;

    @Autowired
    TaskRepository taskRepository;

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(TaskDTO::fromTask).collect(Collectors.toList());
    }

    public List<TaskDTO> findAllByStage(int stageId) {
        Stage stage = findStage(stageId);
        return taskRepository.findAllByStage(stage).stream().map(TaskDTO::fromTask).collect(Collectors.toList());
    }

    public TaskDTO find(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found."));
        return TaskDTO.fromTask(task);
    }

    @Transactional
    public TaskDTO create(TaskDTO newTaskDTO) {
        Task task = new Task();

        newTaskDTO.fillTask(task);
        if (newTaskDTO.stageId > 0) {
            Stage stage = findStage(newTaskDTO.stageId);
            task.setStage(stage);
        }

        taskRepository.save(task);
        return TaskDTO.fromTask(task);
    }

    @Transactional
    public TaskDTO update(TaskDTO taskToUpdate) {
        Task task = taskRepository.findById(taskToUpdate.id).orElseThrow(() -> new TaskNotFoundException(taskToUpdate.toString() + " not found."));
        taskToUpdate.fillTask(task);
        if (taskToUpdate.stageId > 0)
            task.setStage(findStage(taskToUpdate.stageId));

        taskRepository.save(task);
        return TaskDTO.fromTask(task);
    }

    @Transactional
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    private Stage findStage(int id) {
        return stageRepository.findById(id).orElseThrow(() -> new StageNotFoundException("stagre id " + id + "not found."));
    }
}
