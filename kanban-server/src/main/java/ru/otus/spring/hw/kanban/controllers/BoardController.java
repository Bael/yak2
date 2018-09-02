package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.List;

@RestController
public class BoardController {


    BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    @CrossOrigin(origins = "${client.url}")
    public List<BoardDTO> getBoards() {
        return boardService.findAll();
    }

    @PostMapping("/boards")
    @CrossOrigin(origins = "${client.url}")
    BoardDTO newBoard(@RequestBody BoardDTO newBoard) {
        return boardService.create(newBoard);
    }

    @GetMapping("/boards/{id}")
    @CrossOrigin(origins = "${client.url}")
    public BoardDTO getBoard(@PathVariable int id) {
        return boardService.find(id);
    }

    @PutMapping("/boards/{id}")
    @CrossOrigin(origins = "${client.url}")
    BoardDTO updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        return boardService.update(boardDTO);
    }

    @DeleteMapping("/boards/{id}")
    @CrossOrigin(origins = "${client.url}")
    void deleteBoard(@PathVariable int id) {
        boardService.deleteById(id);
    }

}
