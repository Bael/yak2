package ru.otus.spring.hw.kanban.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.spring.hw.kanban.domain.Board;

public interface BoardPagingRepository extends PagingAndSortingRepository<Board, Long> {
}
