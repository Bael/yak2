package ru.otus.spring.hw.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Flux<Board> findAll() {
        return boardRepository.findAll();

    }

    /*
    public BoardDTO find(int id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with " + id + " not found."));
        return BoardDTO.fromBoard(board);
    }

    public BoardDTO create(BoardDTO newBoard) {
        Board board = new Board();
        newBoard.fillBoard(board);
        boardRepository.save(board);
        return BoardDTO.fromBoard(board);
    }

    public BoardDTO update(BoardDTO boardToUpdate) {
        Board board = boardRepository.findById(boardToUpdate.id).orElseThrow(() -> new BoardNotFoundException(boardToUpdate.toString() + " not found."));
        boardToUpdate.fillBoard(board);
        boardRepository.save(board);
        return BoardDTO.fromBoard(board);
    }

    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }*/
}
