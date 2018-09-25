package ru.otus.spring.hw.kanban.service;

import ru.otus.spring.hw.kanban.dto.StageDTO;

import java.util.List;


public interface StageService {

    List<StageDTO> findAll();

    StageDTO find(int id);

    StageDTO create(StageDTO newStage);

    StageDTO update(StageDTO stageToUpdate);

    void deleteById(int id);

}
