package ru.otus.spring.hw.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.exceptions.BoardNotFoundException;
import ru.otus.spring.hw.kanban.exceptions.StageNotFoundException;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
import ru.otus.spring.hw.kanban.repository.StageRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;

    private final BoardRepository boardRepository;

    @Autowired
    public StageServiceImpl(StageRepository stageRepository, BoardRepository boardRepository) {
        this.stageRepository = stageRepository;
        this.boardRepository = boardRepository;
    }

    public List<StageDTO> findAll() {
        return stageRepository.findAll().stream().map(StageDTO::fromStage).collect(Collectors.toList());
    }

    @Override
    public List<StageDTO> findByBoard(int id) {
        Board board = this.boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("Not found with id" + id));

        return this.stageRepository
                        .findStagesByBoard(board).stream()
                        .map(stage -> StageDTO.fromStage(stage))
                        .collect(Collectors.toList());

    }

    public StageDTO find(int id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new StageNotFoundException("Stage with id " + id + " not found."));
        return StageDTO.fromStage(stage);
    }

    @Transactional
    public StageDTO create(StageDTO newStage) {
        Stage stage = new Stage();
        newStage.fillStage(stage);
        if (newStage.boardId > 0) {
            Board board = findBoard(newStage.boardId);
            stage.setBoard(board);
        }

        stageRepository.save(stage);
        return StageDTO.fromStage(stage);
    }

    @Transactional
    public StageDTO update(StageDTO stageToUpdate) {
        Stage stage = stageRepository.findById(stageToUpdate.id).orElseThrow(() -> new StageNotFoundException(stageToUpdate.toString() + " not found."));
        stageToUpdate.fillStage(stage);
        if (stageToUpdate.boardId > 0)
            stage.setBoard(findBoard(stageToUpdate.boardId));

        stageRepository.save(stage);
        return StageDTO.fromStage(stage);
    }

    @Transactional
    public void deleteById(int id) {
        stageRepository.deleteById(id);
    }

    private Board findBoard(int id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("board with id " + id + "not found."));
    }
}
