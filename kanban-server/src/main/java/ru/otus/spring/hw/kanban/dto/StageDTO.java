package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Stage;

import java.util.List;

public class StageDTO {
    public int id;

    private String name;
    private List<TaskDTO> tasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StageDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", boardId=" + boardId +
                ", tasks=" + tasks +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBoardId() {
        return boardId;
    }
    public String description;
    public int boardId;

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public StageDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public StageDTO(int id, String name, String description, int boardId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.boardId = boardId;
    }

  public StageDTO() {
  }

  static public StageDTO fromStage(Stage stage) {
        if(stage.getBoard() != null) {
            return new StageDTO(stage.getId(), stage.getName(), stage.getDescription(), stage.getBoard().getId());
        } else {
            return new StageDTO(stage.getId(), stage.getName(), stage.getDescription());
        }
    }

    public void fillStage(Stage stage) {
      stage.setId(id);
      stage.setName(name);
      stage.setDescription(description);
    }
}
