package ru.otus.spring.hw.kanban.controllers;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.service.StageService;

import java.util.List;

@RestController
public class StageController {


    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping(value = "/stages")
    @Timed(value = "stages.get")
    public List<StageDTO> getStages() {
        return stageService.findAll();
    }

    @GetMapping(value = "/boards/{id}/stages")
    @Timed(value = "stages.getbyboard")
    public List<StageDTO> getStagesByBoard(@PathVariable int id) {
        return stageService.findByBoard(id);
    }


    @PostMapping("/stages")
    @Timed(value = "stages.create")
    StageDTO newStage(@RequestBody StageDTO newStage) {
        return stageService.create(newStage);
    }

    @GetMapping("/stages/{id}")
    @Timed(value = "stages.getbyid")
    public StageDTO getStage(@PathVariable int id) {
        return stageService.find(id);
    }

    @PutMapping("/stages/{id}")
    @Timed(value = "stages.update")
    StageDTO updateStage(@RequestBody StageDTO stageDTO, @PathVariable Long id) {
        return stageService.update(stageDTO);
    }

    @DeleteMapping("/stages/{id}")
    @Timed(value = "stages.delete")
    void deleteStage(@PathVariable int id) {
        stageService.deleteById(id);
    }
}
