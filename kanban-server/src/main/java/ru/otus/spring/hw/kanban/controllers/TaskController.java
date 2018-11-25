package ru.otus.spring.hw.kanban.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.TaskDTO;
import ru.otus.spring.hw.kanban.service.TaskService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/stages/{id}/tasks")
    @PostFilter("hasPermission(filterObject,'READ')")
    @HystrixCommand(groupKey = "tasks", fallbackMethod = "findAllByStageFallback")
    public List<TaskDTO> getTasksByStage(@PathVariable int id) {
        return taskService.findAllByStage(id);
    }

    @GetMapping(value = "/tasks")
    @HystrixCommand(groupKey = "tasks", fallbackMethod = "findAllFallback")
    public List<TaskDTO> getTasks() {
        return taskService.findAll();
    }

    @PostMapping("/tasks")
    @HystrixCommand(groupKey = "tasks", fallbackMethod = "createFallback")
    TaskDTO newTask(Principal principal, @RequestBody TaskDTO newTask) {
        newTask.username = principal.getName();
        return taskService.create(newTask);
    }

    @GetMapping("/tasks/{id}")
    @HystrixCommand(groupKey = "tasks", fallbackMethod = "getTaskFallback")
    public TaskDTO getTask(@PathVariable int id) {
        return taskService.find(id);
    }

    @PutMapping("/tasks/{id}")
    @HystrixCommand(groupKey = "tasks", fallbackMethod = "updateTaskFallback")
    TaskDTO updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        return taskService.update(taskDTO);
    }

    @DeleteMapping("/tasks/{id}")
    @HystrixCommand(groupKey = "tasks")
    void deleteTask(@PathVariable int id) {
        taskService.deleteById(id);
    }



    public List<TaskDTO> findAllByStageFallback(int id) {
        return Collections.emptyList();
    }

    public List<TaskDTO> findAllFallback() {
        return Collections.emptyList();
    }

    TaskDTO createFallback(Principal principal, TaskDTO newTask) {
        return null;
    }


    public TaskDTO getTaskFallback(int id) {
        return null;
    }

    TaskDTO updateTaskFallback(TaskDTO taskDTO, Long id) {
        return null;
    }

}
