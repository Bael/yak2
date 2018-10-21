package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Board;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardDTO {

    public String name;
    public String id;

    public Set<StageDTO> getStages() {
        return stages;
    }

    public void setStages(Set<StageDTO> stages) {
        if (stages == null) {
            this.stages = new HashSet<>();
        } else {
            this.stages = stages;
        }
    }

    public Set<StageDTO> stages;

    public BoardDTO(String name, String id, Set<StageDTO> stages) {
        this.name = name;
        this.id = id;
        this.stages = stages;
    }

    public BoardDTO(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static BoardDTO fromBoard(Board board) {

        BoardDTO boardDTO = new BoardDTO(board.getName(), board.getId());
        boardDTO.setStages(board.getStages()
                                .stream()
                                .map(stage -> StageDTO.fromStage(stage))
                                .collect(Collectors.toSet()));
        return boardDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDTO boardDTO = (BoardDTO) o;
        return id.equals(boardDTO.id) &&
                Objects.equals(name, boardDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public Board fillBoard(Board board) {
        board.setId(this.id);
        board.setName(this.name);
        return board;
    }

    public BoardDTO() {
    }
}
