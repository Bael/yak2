package ru.otus.spring.hw.kanban.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    private Set<Stage> stages =
            new HashSet<>(Arrays.asList(new Stage("plan", "waiting"),
                    new Stage("doing", "start")));

    private List<Board> boards = Arrays.asList(
            new Board("main", stages),
            new Board("home", stages));


    @Before
    public void setUp() {
        boardRepository.deleteAll()
                .thenMany(Flux.fromIterable(boards))
                .flatMap(boardRepository::save)
                .then()
                .block();
    }

    @Test
    public void save() {
        Set<Stage> stages = new HashSet<>(Arrays.asList(new Stage("plan", "waiting"),
                new Stage("doing", "start")));

        Board newBoard = new Board("teamdesk", stages);
        StepVerifier.create(boardRepository.save(newBoard))
                .expectNextMatches(board -> !board.getId().equals(""))
                .verifyComplete();
    }

    @Test
    public void findById() {
        boards.stream()
                .map(Board::getId)
                .forEach(id ->
                        StepVerifier.create(boardRepository.findById(id))
                                .expectNextCount(1)
                                .verifyComplete());
    }



    @Test
    public void findStagesByBoard() {
        boards.stream()
                .map(Board::getId)
                .forEach(id ->
                        StepVerifier.create(boardRepository.findStagesByBoard(id))
                                .expectNextMatches(board ->
                                {
                                    System.out.println(board);

                                    return  board.getStages().size() > 0 && board.getName().isEmpty();

                                })

                                .verifyComplete());
    }


    @Test
    public void findByName() {
        boards.stream()
                .map(Board::getName)
                .forEach(name ->
                        StepVerifier.create(boardRepository.findByName(name))
                                .expectNextCount(1)
                                .verifyComplete());
    }

    @Test
    public void findByNameLike() {
        boards.stream()
                .map(Board::getName)
                .forEach(name ->
                        StepVerifier.create(boardRepository.findBoardsByNameLike(name.substring(0, name.length() - 2)))
                                .expectNextCount(1)
                                .verifyComplete());
    }

    @Test
    public void findByIdNotExist() {
        StepVerifier.create(boardRepository.findById("xyz"))
                .verifyComplete();
    }


}