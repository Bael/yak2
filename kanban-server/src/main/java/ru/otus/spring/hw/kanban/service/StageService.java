package ru.otus.spring.hw.kanban.service;

import ru.otus.spring.hw.kanban.dto.StageDTO;

import java.util.List;


public interface StageService {

    List<StageDTO> findAll();

    List<StageDTO> findByBoard(int id);

    StageDTO find(int id);

    StageDTO create(StageDTO newStage);

    StageDTO update(StageDTO stageToUpdate);

    void deleteById(int id);

}
