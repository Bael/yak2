package ru.otus.spring.hw.kanban.exceptions;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
