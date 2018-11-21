package ru.otus.spring.hw.kanban.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Board {
    public Set<Stage> getStages() {
        if (stages == null) {
            stages = new HashSet<>();
        }
        return stages;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Stage> stages;
    @Column(length = 500)
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Board(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Board{" +
                "stages=" + stages +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public Board() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
