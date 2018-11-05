package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.List;

@RestController
public class BoardController {


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/boards")
    public List<BoardDTO> getBoards() {
        return boardService.findAll();
    }

    @PostMapping("/boards")
    BoardDTO newBoard(@RequestBody BoardDTO newBoard) {
        return boardService.create(newBoard);
    }

    @GetMapping("/boards/{id}")
    public BoardDTO getBoard(@PathVariable int id) {
        return boardService.find(id);
    }

    @PutMapping("/boards/{id}")
    BoardDTO updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        return boardService.update(boardDTO);
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable int id) {
        boardService.deleteById(id);
    }

}
