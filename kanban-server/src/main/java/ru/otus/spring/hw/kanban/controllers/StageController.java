package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.service.StageService;

import java.util.List;

@RestController
public class StageController {


    StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stages")
    public List<StageDTO> getStages() {
        return stageService.findAll();
    }

    @PostMapping("/stages")
    StageDTO newStage(@RequestBody StageDTO newStage) {
        return stageService.create(newStage);
    }

    @GetMapping("/stages/{id}")
    public StageDTO getStage(@PathVariable int id) {
        return stageService.find(id);
    }

    @PutMapping("/stages/{id}")
    StageDTO updateStage(@RequestBody StageDTO stageDTO, @PathVariable Long id) {
        return stageService.update(stageDTO);
    }

    @DeleteMapping("/stages/{id}")
    void deleteStage(@PathVariable int id) {
        stageService.deleteById(id);
    }
}
