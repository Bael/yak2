package ru.otus.spring.hw.kanban.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserNameIsOccupiedException extends RuntimeException {
    public UserNameIsOccupiedException(String username) {
        super("username is occupied" + username);
    }
}
