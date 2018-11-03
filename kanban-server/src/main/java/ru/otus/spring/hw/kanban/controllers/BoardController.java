package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
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
    Mono<BoardDTO> updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable String id) {
        System.out.println(">>> update board: " + boardDTO);
        return boardService.update(boardDTO)
                .map(board -> BoardDTO.fromBoard(board));
    }

    @PostMapping("/api/boards/{id}")
    Mono<BoardDTO> updateBoardByPost(@RequestBody BoardDTO boardDTO, @PathVariable String id) {
        System.out.println(">>> update board: " + boardDTO);
        return boardService.update(boardDTO)
                .map(board -> BoardDTO.fromBoard(board));
    }


    @GetMapping("/api/boards/{id}")
    public Mono<BoardDTO> getBoard(@PathVariable String id) {
        return boardService.find(id).map(BoardDTO::fromBoard);
    }

    @Autowired
    BoardRepository boardRepository;

    @DeleteMapping("/api/boards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<ResponseEntity<Void>> deleteBoard(@PathVariable(value = "id") String id) {
        return boardService.deleteById(id)

                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
