package ru.otus.spring.hw.kanban.domain;


import java.util.HashSet;
import java.util.Set;

public class Stage {
    private int id;
    private String name;
    private String description;
    private Set<Task> tasks;

    public Stage(String name, String description, Board board) {
        this.name = name;
        this.description = description;
    }

    public Stage() {
    }

    public Stage(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Set<Task> getTasks() {
        if (tasks != null)
            return tasks;
        return new HashSet<>();
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
