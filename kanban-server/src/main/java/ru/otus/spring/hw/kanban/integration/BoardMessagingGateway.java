package ru.otus.spring.hw.kanban.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import ru.otus.spring.hw.kanban.dto.BoardDTO;

import java.util.List;

@MessagingGateway(name = "boardGateway")
public interface BoardMessagingGateway {

  @Gateway(requestChannel = "createBoard.input")
  BoardDTO createBoard(Message<BoardDTO> message);

  @Gateway(requestChannel = "getBoards.input")
  List<BoardDTO> getBoards(Message<String> get_all);

  @Gateway(requestChannel = "updateBoard.input")
  BoardDTO updateBoard(Message<BoardDTO> withPayload);

  @Gateway(requestChannel = "deleteBoard.input")
  void deleteBoard(Message<Integer> withPayload);
}
