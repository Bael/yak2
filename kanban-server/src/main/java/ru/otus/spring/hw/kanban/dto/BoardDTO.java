package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Board;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDTO boardDTO = (BoardDTO) o;
        return id == boardDTO.id &&
                Objects.equals(name, boardDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public void fillBoard(Board board) {
        board.setId(this.id);
        board.setName(this.name);
    }

    public BoardDTO() {
    }
}
