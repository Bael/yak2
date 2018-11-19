package ru.otus.spring.hw.kanban.domain.mongo;

public class Task {

  private String id;

  private String name;

  private String description;

  private String executor;

  private int priority;

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

  public Task() {
  }

  public Task(String name, String description, String executor, int priority) {
    this.name = name;
    this.description = description;
    this.executor = executor;
    this.priority = priority;
  }


  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
