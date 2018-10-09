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
        if (task.getStage() != null) {
            return new TaskDTO(task.getId(), task.getName(), task.getDescription(),
                    task.getExecutor(), task.getPriority(), task.getStage().getId());
        } else {
            return new TaskDTO(task.getId(), task.getName(), task.getDescription(),
                    task.getExecutor(), task.getPriority());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public int getPreviousId() {
        return previousId;
    }

    public void setPreviousId(int previousId) {
        this.previousId = previousId;
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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", nextId=" + nextId +
                ", previousId=" + previousId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", executor='" + executor + '\'' +
                ", priority=" + priority +
                ", stageId=" + stageId +
                '}';
    }

    public void fillTask(Task task) {
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setExecutor(executor);
        task.setPriority(priority);
    }
}
