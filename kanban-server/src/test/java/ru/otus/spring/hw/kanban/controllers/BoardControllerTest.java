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


}
