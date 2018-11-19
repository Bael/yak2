package ru.otus.spring.hw.kanban.domain.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document
public class Board {

  private Set<Stage> stages;
  private String name;
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
    if (stages == null) {
      stages = new HashSet<>();
    }

    return stages;
  }

  public void addStage(Stage stage) {
    UUID uuid = UUID.randomUUID();
    stage.setId(uuid.toString());
    stage.setOrder(this.getStages().size());
    this.getStages().add(stage);
  }


  public void setStages(Set<Stage> stages) {
    this.stages = stages;
//        this.stages.forEach(stage -> stage.setId(UUID.randomUUID().toString()));
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

  public void clearStages() {
    this.getStages().clear();
  }
}
