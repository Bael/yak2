package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.TaskDTO;
import ru.otus.spring.hw.kanban.service.TaskService;

import java.util.List;

@RestController
public class TaskController {
    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @CrossOrigin(origins = "${client.url}")
    @RequestMapping(method = RequestMethod.GET, value = "/stages/{id}/tasks")
    public List<TaskDTO> getTasksByStage(@PathVariable int id) {
        return taskService.findAllByStage(id);
    }

    @CrossOrigin(origins = "${client.url}")
    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDTO> getTasks() {
        return taskService.findAll();
    }
    @PostMapping("/tasks")
    @CrossOrigin(origins = "${client.url}")
    TaskDTO newTask(@RequestBody TaskDTO newTask) {
        return taskService.create(newTask);
    }

    @GetMapping("/tasks/{id}")
    @CrossOrigin(origins = "${client.url}")
    public TaskDTO getTask(@PathVariable int id) {
        return taskService.find(id);
    }

    @PutMapping("/tasks/{id}")
    @CrossOrigin(origins = "${client.url}")
    TaskDTO updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        return taskService.update(taskDTO);
    }

    @DeleteMapping("/tasks/{id}")
    @CrossOrigin(origins = "${client.url}")
    void deleteTask(@PathVariable int id) {
        taskService.deleteById(id);
    }
}
