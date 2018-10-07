package ru.otus.spring.hw.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.dto.BoardDTO;
import ru.otus.spring.hw.kanban.exceptions.BoardNotFoundException;
import ru.otus.spring.hw.kanban.repository.BoardRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(BoardDTO::fromBoard).collect(Collectors.toList());
    }

    public BoardDTO find(int id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with " + id + " not found."));
        return BoardDTO.fromBoard(board);
    }

    @Transactional
    public BoardDTO create(BoardDTO newBoard) {
        Board board = new Board();
        newBoard.fillBoard(board);
        boardRepository.save(board);
        return BoardDTO.fromBoard(board);
    }

    @Transactional
    public BoardDTO update(BoardDTO boardToUpdate) {
        Board board = boardRepository.findById(boardToUpdate.id).orElseThrow(() -> new BoardNotFoundException(boardToUpdate.toString() + " not found."));
        boardToUpdate.fillBoard(board);
        boardRepository.save(board);
        return BoardDTO.fromBoard(board);
    }

    @Transactional
    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }
}
