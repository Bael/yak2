package ru.otus.spring.hw.kanban.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.Collections;
import java.util.List;

@RestController
public class BoardController {


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/boards")
    @HystrixCommand(groupKey = "boards", fallbackMethod = "findAllFallback")
    public List<BoardDTO> getBoards() {
        return boardService.findAll();
    }

    @PostMapping("/boards")
    @HystrixCommand(groupKey = "boards", fallbackMethod = "createFallback")
    BoardDTO newBoard(@RequestBody BoardDTO newBoard) {
        return boardService.create(newBoard);
    }

    @GetMapping("/boards/{id}")
    @HystrixCommand(groupKey = "boards", fallbackMethod = "findFallback")
    public BoardDTO getBoard(@PathVariable int id) {
        return boardService.find(id);
    }

    @PutMapping("/boards/{id}")
    @HystrixCommand(groupKey = "boards", fallbackMethod = "updateFallback")
    BoardDTO updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        return boardService.update(boardDTO);
    }

    @DeleteMapping("/boards/{id}")
    @HystrixCommand(groupKey = "boards")
    void deleteBoard(@PathVariable int id) {
        boardService.deleteById(id);
    }

    public List<BoardDTO> findAllFallback() {
        return Collections.emptyList();
    }

    public BoardDTO findFallback(int id) {
        return null;
    }

    public BoardDTO createFallback(BoardDTO newBoard)
    {
        return null;
    }

    public BoardDTO updateFallback(BoardDTO boardToUpdate) {
        return null;
    }

}
