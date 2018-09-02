package ru.otus.spring.hw.kanban.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.spring.hw.kanban.exceptions.BoardNotFoundException;
import ru.otus.spring.hw.kanban.exceptions.StageNotFoundException;
import ru.otus.spring.hw.kanban.exceptions.TaskNotFoundException;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String boardNotFoundHandler(BoardNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(StageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String stageNotFoundHandler(StageNotFoundException ex) {
        return ex.getMessage();
    } @ResponseBody

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taskNotFoundHandler(TaskNotFoundException ex) {
        return ex.getMessage();
    }
}
