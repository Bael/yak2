package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

@RestController
public class BoardController {


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/boards")
    @CrossOrigin(origins = "${client.url}")
    public Flux<BoardDTO> getBoards() {
        return boardService.findAll().map(BoardDTO::fromBoard);
    }
    /*

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
*/
}
