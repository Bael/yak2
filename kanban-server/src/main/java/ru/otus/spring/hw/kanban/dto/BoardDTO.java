package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Board;

import java.util.List;

public class BoardDTO {

    private String name;
    private int id;
    private List<StageDTO> stages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<StageDTO> getStages() {
        return stages;
    }

    public void setStages(List<StageDTO> stages) {
        this.stages = stages;
    }

    public BoardDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static BoardDTO fromBoard(Board board) {
        return new BoardDTO(board.getName(), board.getId());
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public void fillBoard(Board board) {
        board.setId(this.id);
        board.setName(this.name);
    }

    public BoardDTO() {
    }
}
