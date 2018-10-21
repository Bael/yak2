package ru.otus.spring.hw.kanban.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.service.BoardService;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BoardControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private BoardService service;

    private Set<Stage> stages;


    private List<Board> boards;

    @Before
    public void setUp() {

        stages = new HashSet<>(Arrays.asList(new Stage("plan", "waiting"),
                new Stage("doing", "start")));
        boards = Arrays.asList(
                new Board("main", stages),
                new Board("home", stages));

        List<Board> newboards = new ArrayList<>();

        service.deleteAll()
                .thenMany(Flux.fromIterable(boards))
                .flatMap(board -> service.create(BoardDTO.fromBoard(board)))
                .doOnNext(System.out::println)
                .doOnNext(board -> newboards.add(board))
                .then()
                .block();
        boards = newboards;

    }

    @Test
    public void testGetAllOfficers() {
        client.get().uri("/boards")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(BoardDTO.class)
                .hasSize(2)
                .consumeWith(System.out::println);
    }

    @Test
    public void testGetBoard() {
        client.get().uri("/boards/{id}", boards.get(0).getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoardDTO.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testGetNonExistingBoard() {
        client.get().uri("/boards/{id}", boards.get(0).getId().substring(2))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(BoardDTO.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testCreateBoard() {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.name = "new board";

        client.post().uri("/boards")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(boardDTO), BoardDTO.class)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("new board")
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateBoard() {
        BoardDTO boardDTO = BoardDTO.fromBoard(boards.get(0));
        boardDTO.name = "changed board";
        System.out.println(boardDTO.id +  boardDTO);

        client.post().uri("/boards/{id}", boardDTO.id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(boardDTO), BoardDTO.class)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("new board")
                .consumeWith(System.out::println);
    }

//    @Test
//    public void testUpdateOfficer() {
//        BoardDTO boardDTO = BoardDTO.fromBoard(boards.get(0));
//        boardDTO.name = "changed board";
//
//        client.put().uri("/boards/{id}", boardDTO.id)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .body(Mono.just(boardDTO), BoardDTO.class)
//                .exchange()
////                .expectStatus().isCreated()
//                .expectStatus().is2xxSuccessful()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody()
//                .jsonPath("$.id").isEqualTo(boardDTO.id)
//                .jsonPath("$.name").isEqualTo("changed board")
//                .consumeWith(System.out::println);
//    }

    /*
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
    public void getNonExistingBoard() throws Exception {

        given(this.boardService.find(1))
                .willThrow(new BoardNotFoundException("Board with 1 not found."));

        this.mvc.perform(MockMvcRequestBuilders.get("/boards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }


    @Test
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
    public void deleteBoard() throws Exception {
        int id = 1000000;
        this.mvc.perform(
                MockMvcRequestBuilders.delete("/boards/" + id)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    } */
}
