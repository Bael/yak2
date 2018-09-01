package ru.otus.spring.hw.kanban.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw.kanban.domain.Task;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void save() {

        Task task = new Task("make app", "fast and pretty", "me", 5);
        taskRepository.save(task);

        Optional<Task> task1 = taskRepository.findById(task.getId());
        Assert.assertTrue(task1.isPresent());
        Assert.assertEquals(task.getName(), task.getName());

    }
}