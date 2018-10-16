package ru.otus.spring.hw.kanban.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class Board {

    private Set<Stage> stages;
    private String name;
    @Id
    private String id;

    public Board(String name, Set<Stage> stages) {
        this.stages = stages;
        this.name = name;
    }
    public Board(String name) {
        this.name = name;
    }

    public Board() {
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    @Override
    public String toString() {
        return "Board{" +
                "stages=" + stages +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
