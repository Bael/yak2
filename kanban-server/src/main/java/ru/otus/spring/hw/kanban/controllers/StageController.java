package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.service.BoardService;
import ru.otus.spring.hw.kanban.service.StageService;

@Controller
public class StageController {
    StageService stageService;
    BoardService boardService;

    @Autowired
    public StageController(StageService stageService, BoardService boardService) {
        this.stageService = stageService;
        this.boardService = boardService;
    }


    @RequestMapping(value = {"/boards/stages/update"}, method = RequestMethod.POST)
    public String saveStage(@RequestParam("boardId") int boardId, @RequestParam("stageId") int stageId, Model model, //
                            @ModelAttribute StageDTO stage) {

        StageDTO stageDTO = stageService.find(stageId);
        stageDTO.setName(stage.getName());
        stageService.update(stageDTO);

        BoardDTO board = boardService.find(boardId);
        board.setStages(stageService.findByBoard(boardId));
        model.addAttribute("board", board);
        return "board-edit";
    }


    @RequestMapping(value = {"/boards/stages"}, method = RequestMethod.GET)
    public String editStage2(@RequestParam("boardId") int boardId, @RequestParam("stageId") int stageId,
                             Model model) {

        StageDTO stageDTO = stageService.find(stageId);
        BoardDTO boardDTO = boardService.find(boardId);

        model.addAttribute("stage", stageDTO);
        model.addAttribute("board", boardDTO);
        return "stage-edit";


    }


    @RequestMapping(value = {"/boards/stages/"}, method = RequestMethod.POST)
    public String createStage(@RequestParam("boardId") int boardId, Model model, StageDTO stageDTO) {

        stageService.create(stageDTO);
        BoardDTO board = boardService.find(boardId);
        board.setStages(stageService.findByBoard(boardId));
        model.addAttribute("board", board);
        return "board-edit";
    }

    @RequestMapping(value = {"/boards/stages/delete"}, method = RequestMethod.POST)
    public String deleteStage(@RequestParam("boardId") int boardId, @RequestParam("stageId") int stageId, Model model, //
                              @ModelAttribute StageDTO stageDTO) {

        stageService.deleteById(stageId);
        BoardDTO board = boardService.find(boardId);
        board.setStages(stageService.findByBoard(boardId));

        model.addAttribute("board", board);
        return "board-edit";
    }

}
