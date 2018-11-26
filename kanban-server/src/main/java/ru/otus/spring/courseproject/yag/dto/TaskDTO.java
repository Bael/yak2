package ru.otus.spring.courseproject.yag.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TaskDTO {

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
