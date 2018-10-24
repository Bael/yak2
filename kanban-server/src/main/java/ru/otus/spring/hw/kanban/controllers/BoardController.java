package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

@RestController
public class BoardController {


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/api/boards")
    public Flux<BoardDTO> getBoards() {
        return boardService.findAll().map(BoardDTO::fromBoard);
    }


    @PostMapping("/api/boards")
    Mono<BoardDTO> newBoard(@RequestBody BoardDTO newBoard) {
        return boardService.create(newBoard).map(board -> BoardDTO.fromBoard(board));
    }

    @PutMapping("/api/boards/{id}")
    Mono<BoardDTO> updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        System.out.println(">>> update board: " + boardDTO);
        return boardService.update(boardDTO)
                .map(board -> BoardDTO.fromBoard(board));
    }

    @PostMapping("/api/boards/{id}")
    Mono<BoardDTO> updateBoardByPost(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        System.out.println(">>> update board: " + boardDTO);
        return boardService.update(boardDTO)
                .map(board -> BoardDTO.fromBoard(board));
    }


    @GetMapping("/api/boards/{id}")
    public Mono<BoardDTO> getBoard(@PathVariable String id) {
        return boardService.find(id).map(BoardDTO::fromBoard);
    }

    @DeleteMapping("/api/boards/{id}")
    void deleteBoard(@PathVariable String id) {
        boardService.deleteById(id);
    }

    /*

.thenEmpty(s -> {
                    throw new BoardNotFoundException("");
                })
    @GetMapping("/boards/{id}")
    @CrossOrigin(origins = "${client.url}")
    public BoardDTO getBoard(@PathVariable int id) {
        return boardService.find(id);
    }


*/
}
