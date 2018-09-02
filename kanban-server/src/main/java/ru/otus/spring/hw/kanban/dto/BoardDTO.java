package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Board;

public class BoardDTO {

    public String name;
    public int id;

    public BoardDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static BoardDTO fromBoard(Board board) {
        return new BoardDTO(board.getName(), board.getId());
    }

    public void fillBoard(Board board) {
        board.setId(this.id);
        board.setName(this.name);
    }

    public BoardDTO() {
    }
}
