package ru.otus.spring.hw.kanban.exceptions;

public class StageNotFoundException extends RuntimeException {
    public StageNotFoundException(String message) {
        super(message);
    }
}
