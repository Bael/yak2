package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.service.BoardService;
import ru.otus.spring.hw.kanban.service.StageService;
import ru.otus.spring.hw.kanban.service.TaskService;

import java.util.List;

@Controller
public class BoardController {


    BoardService boardService;
    StageService stageService;
    TaskService taskService;

    @Autowired
    public BoardController(BoardService boardService, StageService stageService, TaskService taskService) {
        this.boardService = boardService;
        this.stageService = stageService;
        this.taskService = taskService;
    }


    @GetMapping({"/boards", "/"})
    public String listPage(Model model) {
        List<BoardDTO> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "boards-list";
    }

    @PostMapping("/boards/")
    public String createBoard(Model model, BoardDTO boardDTO) {
        boardService.create(boardDTO);
        List<BoardDTO> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "boards-list";
    }


    @GetMapping("/boards/edit")
    public String getBoard(@RequestParam("id") int id, Model model) {
        BoardDTO board = boardService.find(id);

        board.setStages(stageService.findByBoard(id));
        model.addAttribute("board", board);
        return "board-edit";
    }


    @PutMapping("/boards/{id}")
    BoardDTO updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        return boardService.update(boardDTO);
    }

    @RequestMapping(value = {"/boards/edit"}, method = RequestMethod.POST)
    public String saveBoard(@RequestParam("id") int id, Model model, //
                            @ModelAttribute BoardDTO boardToUpdate) {
        boardService.update(boardToUpdate);
        model.addAttribute("boards", boardService.findAll());
        return "redirect:/boards";
    }

    @RequestMapping(value = {"/boards/delete/"}, method = RequestMethod.POST)
    public String deleteBoard(@RequestParam("id") int id, Model model) {
        try {
            boardService.deleteById(id);
        } catch (Exception ex) {
            model.addAttribute("error", ex.toString());
            return "redirect:/error";
        }
        model.addAttribute("boards", boardService.findAll());
        return "redirect:/boards";
    }

    @GetMapping("/boards/")
    public String showBoard(@RequestParam("id") int id, Model model) {
        BoardDTO board = boardService.find(id);
        List<StageDTO> stages = stageService.findByBoard(id);
        stages.forEach(stageDTO -> stageDTO.setTasks(taskService.findAllByStage(stageDTO.id)));
        board.setStages(stages);
        model.addAttribute("board", board);
        return "board-show";
    }


    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable int id) {
        boardService.deleteById(id);
    }

}
