package ru.otus.spring.hw.kanban.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.spring.hw.kanban.domain.Task;

@Data
@AllArgsConstructor
@Builder
public class TaskDTO {
    public int id;
    public int nextId;
    public int previousId;
    public String name;
    public String description;
    public String executor;
    public String username;
    public int priority;
    public int stageId;

    public TaskDTO() {
    }

    public TaskDTO(int id, String name, String description, String executor, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.priority = priority;
    }

    public TaskDTO(int id, String name, String description, String executor, int priority, int stageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.priority = priority;
        this.stageId = stageId;
    }

    public static TaskDTO fromTask(Task task) {
        TaskDTOBuilder builder = TaskDTO.builder().id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .executor(task.getExecutor())
                .priority(task.getPriority());


        if (task.getStage() != null) {
            builder.stageId(task.getStage().getId());
        }

        if (task.getUser() != null) {
            builder.username(task.getUser().getUsername());
        }
        return builder.build();
    }

    public void fillTask(Task task) {
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setExecutor(executor);
        task.setPriority(priority);
    }
}
