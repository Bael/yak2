package ru.otus.spring.hw.kanban.domain;

import lombok.Data;
import ru.otus.spring.hw.kanban.security.UserAccount;

import javax.persistence.*;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column()
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 500)
    private String executor;

    @Column
    private int priority;

    @ManyToOne
    private UserAccount user;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", executor='" + executor + '\'' +
                ", priority=" + priority +
                '}';
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Stage stage;

    public Task() {
    }

    public Task(String name, String description, String executor, int priority) {
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.priority = priority;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}
