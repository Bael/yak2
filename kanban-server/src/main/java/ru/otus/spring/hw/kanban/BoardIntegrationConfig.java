package ru.otus.spring.hw.kanban;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.channel.MessageChannels;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
import ru.otus.spring.hw.kanban.service.BoardService;

@Configuration
@IntegrationComponentScan
@EnableIntegration
public class BoardIntegrationConfig {

  @Bean(name = "createBoard.input")
  public DirectChannel createBoardInputChannel() {
    return MessageChannels.direct("createBoard.input").datatype(BoardDTO.class).get();
  }

  @Bean(name = "getBoards.input")
  public DirectChannel getBoardsChannel() {
    return MessageChannels.direct("getBoards.input").datatype(String.class).get();
  }

  @Bean(name = "updateBoard.input")
  public DirectChannel updateBoardsChannel() {
    return MessageChannels.direct("updateBoard.input").datatype(BoardDTO.class).get();
  }

  @Bean(name = "deleteBoard.input")
  public DirectChannel deleteBoardChannel() {
    return MessageChannels.direct("deleteBoard.input").datatype(Integer.class).get();
  }


  @Bean
  public IntegrationFlow createBoard(BoardService boardService, BoardRepository boardRepository) {
    return f -> f
      .<BoardDTO, Board>transform(source -> source.fillBoard(new Board()))
      .<Board>handle((payload, headers) -> boardRepository.save(payload))
      .<Board, BoardDTO>transform(source -> BoardDTO.fromBoard(source));
  }


  @Bean
  public IntegrationFlow getBoards(BoardRepository boardRepository) {
    return f -> f
      .<String, Boolean>route(s -> s != null && s.length() > 0,
        mapping ->
          mapping
            .subFlowMapping(true,
              flow -> flow.<String>handle((payload, headers) -> boardRepository.findBoardsByName(payload)))
            .subFlowMapping(false,
              flow -> flow.handle((payload, headers) -> boardRepository.findAll()))
      )
      .split()
      .<Board, BoardDTO>transform(BoardDTO::fromBoard)
      .aggregate();
  }

  @Bean
  public IntegrationFlow updateBoard(BoardRepository boardRepository) {
    return f -> f
      .<BoardDTO, Board>transform(source -> {
          Board board = boardRepository.findById(source.id).orElseThrow(() -> new RuntimeException("Not found board"));
          source.fillBoard(board);
          return board;
        }
      )
      .<Board>handle((payload, headers) -> boardRepository.save(payload))
      .<Board, BoardDTO>transform(BoardDTO::fromBoard);
  }

  @Bean
  public IntegrationFlow deleteBoard(BoardService boardService, BoardRepository boardRepository) {
    return f -> f
      .<Integer, Board>transform(source -> boardRepository.findById(source).orElseThrow(() -> new RuntimeException("Object not founded")))
      .<Board>handle((payload, headers) -> boardService.delete(payload));

  }


}
