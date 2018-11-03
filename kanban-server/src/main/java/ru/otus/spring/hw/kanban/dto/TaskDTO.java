package ru.otus.spring.hw.kanban.dto;


import ru.otus.spring.hw.kanban.domain.Task;

public class TaskDTO {
    public String id;
 ;
    public String name;
    public String description;
    public String executor;
    public int priority;
    public String stageId;

    public TaskDTO() {
    }

    public TaskDTO(String id, String name, String description, String executor, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.priority = priority;
    }

    public TaskDTO(String id, String name, String description, String executor, int priority, String stageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.priority = priority;
        this.stageId = stageId;
    }

    public static TaskDTO fromTask(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.name = task.getName();
        return taskDTO;
    }


    public Task fillTask(Task task) {
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setExecutor(executor);
        task.setPriority(priority);
        return task;
    }
}
