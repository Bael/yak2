package ru.otus.spring.hw.kanban;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.repository.BoardRepository;


@Service
@Slf4j
public class BoardHealthCheck implements HealthIndicator {
  @Autowired
  BoardRepository boardRepository;

  @Override
  public Health health() {
    if (boardRepository.findAll().size() > 2) {
      return Health.up().build();
    }
    log.warn("Not enough boards!!");
    return Health.down().build();
  }
}
