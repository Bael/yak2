package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Stage;

public class StageDTO {
    public int id;
    public String name;
    public String description;
    public int boardId;


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
