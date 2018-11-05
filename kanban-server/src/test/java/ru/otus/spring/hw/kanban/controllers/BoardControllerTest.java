package ru.otus.spring.hw.kanban.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.exceptions.BoardNotFoundException;
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

    private JacksonTester<BoardDTO> jsonBoard;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @WithMockUser(value = "spring")
    public void getBoards() throws Exception {

        List<BoardDTO> list = new ArrayList<>();
        BoardDTO board = new BoardDTO();
        board.id = 1;
        board.name = "board 1";
        list.add(board);

        BoardDTO board2 = new BoardDTO();
        board2.id = 2;
        board2.name = "board 2";
        list.add(board2);

        given(this.boardService.findAll())
                .willReturn(list);

        this.mvc.perform(MockMvcRequestBuilders.get("/boards").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString("board 1")))
                .andExpect(content().string(Matchers.containsString("board 2")));

    }

    @Test
    public void newBoard() {
    }

    @Test
    @WithMockUser(value = "spring")
    public void getBoard() throws Exception {
        int id = 1;
        BoardDTO board = new BoardDTO();
        board.id = id;
        board.name = "board 1";

        given(this.boardService.find(id))
                .willReturn(board);

        this.mvc.perform(MockMvcRequestBuilders.get("/boards/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString("board 1")));
    }

    @Test
    @WithMockUser(value = "spring")
    public void getNonExistingBoard() throws Exception {

        given(this.boardService.find(1))
                .willThrow(new BoardNotFoundException("Board with 1 not found."));

        this.mvc.perform(MockMvcRequestBuilders.get("/boards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }


    @Test
    @WithMockUser(value = "spring")
    public void updateBoard() throws Exception {
        int id = 1;
        BoardDTO board = new BoardDTO();
        board.id = id;
        board.name = "board 1";

        given(this.boardService.find(id))
                .willReturn(board);

        BoardDTO changedBoard = new BoardDTO();
        changedBoard.id = id;
        changedBoard.name = "board 100";

        given(this.boardService.update(changedBoard))
                .willReturn(changedBoard);


        this.mvc.perform(
                MockMvcRequestBuilders.put("/boards/" + id)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(jsonBoard.write(changedBoard).getJson())
        )

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString("board 100")));
    }

    @Test
    @WithMockUser(value = "spring")
    public void deleteBoard() throws Exception {
        int id = 1000000;
        this.mvc.perform(
                MockMvcRequestBuilders.delete("/boards/" + id)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
