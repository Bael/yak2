package ru.otus.spring.hw.kanban.dto;


import ru.otus.spring.hw.kanban.domain.Task;

public class TaskDTO {
    public int id;
    public int nextId;
    public int previousId;
    public String name;
    public String description;
    public String executor;
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
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.name = task.getName();
        return taskDTO;
    }


    public void fillTask(Task task) {
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setExecutor(executor);
        task.setPriority(priority);
    }
}
