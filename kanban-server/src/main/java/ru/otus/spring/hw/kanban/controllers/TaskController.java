package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.dto.TaskDTO;
import ru.otus.spring.hw.kanban.service.StageService;
import ru.otus.spring.hw.kanban.service.TaskService;

import java.util.List;

@RestController
public class TaskController {
    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDTO> getTasks() {
        return taskService.findAll();
    }

    @PostMapping("/tasks")
    TaskDTO newTask(@RequestBody TaskDTO newTask) {
        return taskService.create(newTask);
    }

    @GetMapping("/tasks/{id}")
    public TaskDTO getTask(@PathVariable int id) {
        return taskService.find(id);
    }

    @PutMapping("/tasks/{id}")
    TaskDTO updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        return taskService.update(taskDTO);
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable int id) {
        taskService.deleteById(id);
    }
}
