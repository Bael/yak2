package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.TaskDTO;
import ru.otus.spring.hw.kanban.service.BoardService;
import ru.otus.spring.hw.kanban.service.TaskService;

import java.util.List;

@Controller
public class TaskController {
    TaskService taskService;
    BoardService boardService;

    @Autowired
    public TaskController(TaskService taskService, BoardService boardService) {
        this.taskService = taskService;
        this.boardService = boardService;
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

    @PostMapping("/tasks/")
    String newTask(@RequestParam("stageId") int stageId,
                   @RequestParam("boardId") int boardId,
                   Model model, TaskDTO taskDTO) {
        taskDTO.stageId = stageId;
        TaskDTO newtask = taskService.create(taskDTO);
        return "redirect:/boards/?id=" + boardId;
    }

    @PostMapping("/tasks/move/")
    String moveTask(@RequestParam("id") int id, @RequestParam("boardId") int boardId) {
        TaskDTO taskDTO = taskService.find(id);
        taskService.moveToNextStage(taskDTO);
        return "redirect:/boards/?id=" + boardId;
    }

    @GetMapping("/tasks/edit/")
    String getEditPage(@RequestParam("id") int id, @RequestParam("boardId") int boardId, Model model) {
        TaskDTO taskDTO = taskService.find(id);
        model.addAttribute("task", taskDTO);
        model.addAttribute("board", boardService.find(boardId));
        return "task-edit";
    }

    @PostMapping("/tasks/edit/")
    String postEditPage(@RequestParam("id") int id, @RequestParam("boardId") int boardId, Model model, TaskDTO taskDTO) {
        taskService.update(taskDTO);
        model.addAttribute("task", taskDTO);
        model.addAttribute("board", boardService.find(boardId));
        return "redirect:/boards/?id=" + boardId;
    }

    @PostMapping("/tasks/delete")
    String deleteTask(@RequestParam("id") int id, @RequestParam("boardId") int boardId, Model model) {
        taskService.deleteById(id);
        model.addAttribute("board", boardService.find(boardId));
        return "redirect:/boards/?id=" + boardId;
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
}
