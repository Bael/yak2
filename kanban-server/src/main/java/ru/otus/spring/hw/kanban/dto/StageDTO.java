package ru.otus.spring.hw.kanban.dto;

import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StageDTO {
    public String id;
    public String name;
    public String description;
    public String boardId;
    public int order;

    public Set<TaskDTO> getTasks() {
        return tasks == null ? new HashSet<>() : tasks;
    }

    public Set<TaskDTO> tasks;

    public StageDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public StageDTO(String id, String name, String description, String boardId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.boardId = boardId;
    }


    public StageDTO() {
    }

    public static StageDTO fromStage(Stage stage) {
        StageDTO stageDTO = new StageDTO(stage.getId(), stage.getName(), stage.getDescription());
        stageDTO.order = stage.getOrder();
        stageDTO.tasks = stage.getTasks().stream().map(task -> TaskDTO.fromTask(task)).collect(Collectors.toSet());
        return stageDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageDTO stageDTO = (StageDTO) o;
        return id == stageDTO.id &&
                boardId == stageDTO.boardId &&
                Objects.equals(name, stageDTO.name) &&
                Objects.equals(description, stageDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, boardId);
    }

    public Stage fillStage(Stage stage) {
        stage.setId(id);
        stage.setName(name);
        stage.setDescription(description);
        stage.setOrder(order);

        Set<Task> tasks = this.getTasks().stream().map(taskDTO -> taskDTO.fillTask(new Task())).collect(Collectors.toSet());
        stage.setTasks(tasks);
        return stage;
    }
}
