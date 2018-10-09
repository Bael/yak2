package ru.otus.spring.hw.kanban.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BoardControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private BoardService boardService;

  @Test
  public void getBoards() throws Exception {

    List<BoardDTO> list = new ArrayList<>();
    BoardDTO board = new BoardDTO();
    board.setId(1);
    board.setName("board 1");
    list.add(board);

    BoardDTO board2 = new BoardDTO();
    board2.setId(2);
    board2.setName("board 2");
    list.add(board2);

    given(this.boardService.findAll())
      .willReturn(list);

    this.mvc.perform(MockMvcRequestBuilders.get("/boards").accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(content().string("[{\"name\":\"board 1\",\"id\":1},{\"name\":\"board 2\",\"id\":2}]"));

  }

  @Test
  public void newBoard() {
  }

  @Test
  public void getBoard() {
  }

  @Test
  public void updateBoard() {
  }

  @Test
  public void deleteBoard() {
  }
}
