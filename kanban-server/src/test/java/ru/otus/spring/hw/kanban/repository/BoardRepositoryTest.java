package ru.otus.spring.hw.kanban.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.kanban.domain.Board;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Test
    public void findBoardsByNameLike() {

        boardRepository.save(new Board("books to read"));

        boardRepository.save(new Board("programs to write"));

        boardRepository.save(new Board("journeys to make"));


        List<Board> boards = boardRepository.findBoardsByNameLike("journey");
        Assert.assertTrue(boards.stream().allMatch(board1 -> board1.getName().equals("journeys to make")));


    }
}