package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.integration.BoardMessagingGateway;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.List;

@RestController
public class BoardController {


    private final BoardService boardService;

    private final BoardMessagingGateway boardMessagingGateway;

    @Autowired
    public BoardController(BoardService boardService, BoardMessagingGateway boardMessagingGateway) {
        this.boardService = boardService;
        this.boardMessagingGateway = boardMessagingGateway;
    }

    @GetMapping(value = "/boards")
    public List<BoardDTO> getBoards() {
        return boardMessagingGateway.getBoards(MessageBuilder.withPayload("").build());
    }

    @PostMapping("/boards")
    BoardDTO newBoard(@RequestBody BoardDTO newBoard) {
        Message<BoardDTO> message = MessageBuilder
                .withPayload(newBoard)
                .build();
        BoardDTO dto = boardMessagingGateway.createBoard(message);
        return dto;
    }

    @GetMapping("/boards/{id}")
    public BoardDTO getBoard(@PathVariable int id) {
        return boardService.find(id);
    }

    @PutMapping("/boards/{id}")
    BoardDTO updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long id) {
        return boardMessagingGateway.updateBoard(MessageBuilder.withPayload(boardDTO).build());
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable int id) {
        boardMessagingGateway.deleteBoard(MessageBuilder.withPayload(id).build());

    }

}
