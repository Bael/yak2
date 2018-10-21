package ru.otus.spring.hw.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.exceptions.BoardNotFoundException;
import ru.otus.spring.hw.kanban.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Flux<Board> findAll() {
        return boardRepository.findAll();

    }

    @Override
    public Mono<Board> find(String id) {
        return boardRepository.findById(id)
                .switchIfEmpty(Mono.error(new BoardNotFoundException("not found board with id" + id)));
    }

    @Override
    public Mono<Board> create(BoardDTO newBoard) {
        Board board = new Board();
        newBoard.fillBoard(board);
        return boardRepository.save(board);
    }


    @Override
    public Mono<Board> update(BoardDTO boardToUpdate) {
        System.out.println("UPDATE CALLED");
        return boardRepository.findById(boardToUpdate.id)
                .switchIfEmpty(Mono.error(new BoardNotFoundException("not found board with id" + boardToUpdate.id)))
                .flatMap(existingBoard -> {
                    existingBoard.setName(boardToUpdate.name);
                    return boardRepository.save(existingBoard);
                });
    }

    @Override
    public Mono<Void> deleteAll() {
        return boardRepository.deleteAll();
    }

    public void deleteById(String id) {
        boardRepository.deleteById(id);
    }

}
