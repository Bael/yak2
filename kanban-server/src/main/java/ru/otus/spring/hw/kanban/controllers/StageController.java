package ru.otus.spring.hw.kanban.controllers;

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

    @CrossOrigin(origins = "${client.url}")
    @GetMapping(value = "/stages")
    public List<StageDTO> getStages() {
        return stageService.findAll();
    }

    @CrossOrigin(origins = "${client.url}")
    @GetMapping(value = "/boards/{id}/stages")
    public List<StageDTO> getStagesByBoard(@PathVariable int id) {
        return stageService.findByBoard(id);
    }


    @PostMapping("/stages")
    @CrossOrigin(origins = "${client.url}")
    StageDTO newStage(@RequestBody StageDTO newStage) {
        return stageService.create(newStage);
    }

    @CrossOrigin(origins = "${client.url}")
    @GetMapping("/stages/{id}")
    public StageDTO getStage(@PathVariable int id) {
        return stageService.find(id);
    }

    @CrossOrigin(origins = "${client.url}")
    @PutMapping("/stages/{id}")
    StageDTO updateStage(@RequestBody StageDTO stageDTO, @PathVariable Long id) {
        return stageService.update(stageDTO);
    }

    @CrossOrigin(origins = "${client.url}")
    @DeleteMapping("/stages/{id}")
    void deleteStage(@PathVariable int id) {
        stageService.deleteById(id);
    }
}
