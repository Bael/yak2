package ru.otus.spring.hw.kanban.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class StageRepositoryTest {

    @Autowired
    StageRepository stageRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void findStagesByBoardId() {

        Board board = new Board("main");
        boardRepository.save(board);

        stageRepository.save(new Stage("plan", "to do somehow", board));
        stageRepository.save(new Stage("work", "work work", board));

        Board board2 = new Board("entertain");
        boardRepository.save(board2);

        stageRepository.save(new Stage("playing", "1 hour", board2));

        List<Stage> stages = stageRepository.findStagesByBoard(board);
        Assert.assertEquals(2, stages.size());
        Assert.assertTrue(stages.stream().anyMatch(stage -> stage.getName().equals("plan")));
        Assert.assertTrue(stages.stream().anyMatch(stage -> stage.getName().equals("work")));
        System.out.println(stages);



        List<Stage> stages2 = stageRepository.findStagesByBoard(board2);
        Assert.assertEquals(1, stages2.size());
        Assert.assertTrue(stages2.stream().anyMatch(stage -> stage.getName().equals("playing")));

        System.out.println(stages2);





    }
}