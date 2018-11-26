package ru.otus.spring.hw.kanban.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProjectTaskDTO {

  private long id;
  @JsonProperty("start_date")
  private String startDate;
  @JsonProperty("text")
  private String description;
  private double progress;
  private int duration;
  private Long parent;
  private Long project;
  private String executor;

}
