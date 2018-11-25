package ru.otus.spring.hw.kanban.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.service.StageService;

import java.util.Collections;
import java.util.List;

@RestController
public class StageController {


    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping(value = "/stages")
    @HystrixCommand(groupKey = "stages", fallbackMethod = "findAllFallback")
    public List<StageDTO> getStages() {
        return stageService.findAll();
    }

    @GetMapping(value = "/boards/{id}/stages")
    @HystrixCommand(groupKey = "stages", fallbackMethod = "findAllByBoardFallback")
    public List<StageDTO> getStagesByBoard(@PathVariable int id) {
        return stageService.findByBoard(id);
    }


    @PostMapping("/stages")
    @HystrixCommand(groupKey = "stages", fallbackMethod = "createStageFallback")
    StageDTO newStage(@RequestBody StageDTO newStage) {
        return stageService.create(newStage);
    }

    @GetMapping("/stages/{id}")
    @HystrixCommand(groupKey = "stages", fallbackMethod = "getStageFallback")
    public StageDTO getStage(@PathVariable int id) {
        return stageService.find(id);
    }

    @PutMapping("/stages/{id}")
    @HystrixCommand(groupKey = "stages", fallbackMethod = "updateStageFallback")
    StageDTO updateStage(@RequestBody StageDTO stageDTO, @PathVariable Long id) {
        return stageService.update(stageDTO);
    }

    @DeleteMapping("/stages/{id}")
    @HystrixCommand(groupKey = "stages", fallbackMethod = "deleteStageFallback")
    void deleteStage(@PathVariable int id) {
        stageService.deleteById(id);
    }

    public List<StageDTO> findAllFallback() {
        return Collections.emptyList();
    }

    public List<StageDTO> findAllByBoardFallback(int id) {
        return Collections.emptyList();
    }

    StageDTO createStageFallback(StageDTO newStage) {
        return null;
    }


    public StageDTO getStageFallback(int id) {
        return null;
    }

    StageDTO updateStageFallback(StageDTO stageDTO, Long id) {
        return null;
    }

    void deleteStageFallback(int id) {

    }
}
