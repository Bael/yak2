package ru.otus.spring.hw.kanban.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.List;

@RestController
@Slf4j
@Timed
public class BoardController {


    private final BoardService boardService;



    @Autowired
    public BoardController(BoardService boardService) {
        log.info("BoardController constructor called!");
        this.boardService = boardService;
    }

    @GetMapping(value = "/boards")
    @Timed(value = "boards.getall")
    public List<BoardDTO> getBoards() {
        return boardService.findAll();
    }

    @PostMapping("/boards")
    @Timed(value = "boards.create")
    BoardDTO newBoard(@RequestBody BoardDTO newBoard) {
        return boardService.create(newBoard);
    }

    @GetMapping("/boards/{id}")
    @Timed(value = "boards.getbyid")
    public BoardDTO getBoard(@PathVariable int id) {
        return boardService.find(id);
    }

    @PutMapping("/boards/{id}")
    @Timed(value = "boards.update")
    BoardDTO updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        return boardService.update(boardDTO);
    }

    @DeleteMapping("/boards/{id}")
    @Timed(value = "boards.delete")
    void deleteBoard(@PathVariable int id) {
        boardService.deleteById(id);
    }

}
