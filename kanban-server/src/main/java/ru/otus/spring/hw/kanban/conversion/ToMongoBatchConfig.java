package ru.otus.spring.hw.kanban.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.hw.kanban.conversion.BoardItemProcessor;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
import ru.otus.spring.hw.kanban.repository.mongo.BoardMongoRepository;
import org.springframework.batch.core.*;

import java.util.List;

@EnableBatchProcessing
public class ToMongoBatchConfig {

  private final Logger logger = LoggerFactory.getLogger("Batch");

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;


  @Autowired
  BoardItemProcessor boardItemProcessor;

  @Autowired
  BoardRepository boardRepository;
  @Autowired
  BoardMongoRepository boardMongoRepository;

  @Bean
  public ItemReader<Board> reader() {
    return new ItemReader<Board>() {

      private Integer currentId = 0;

      @Override
      public Board read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Board board = boardRepository.findFirstByIdGreaterThan(currentId).orElse(null);
        if (board != null) {
          currentId = board.getId();
        }

        return board;
      }
    };
  }

  @Bean
  public ItemWriter<ru.otus.spring.hw.kanban.domain.mongo.Board> writer() {
    return items -> items.forEach(o -> boardMongoRepository.save(o));
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
      .<Board, ru.otus.spring.hw.kanban.domain.mongo.Board>chunk(5)
      .reader(reader())
      .processor(boardItemProcessor)
      .writer(writer())
      .listener(new ItemReadListener<Board>() {
        public void beforeRead() { logger.info("Начало чтения"); }
        public void afterRead(Board o) { logger.info("Конец чтения " + o.toString()); }
        public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
      })
      .listener(new ItemWriteListener() {
        public void beforeWrite(List list) { logger.info("Начало записи"); }
        public void afterWrite(List list) { logger.info("Конец записи"); }
        public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
      })
      .listener(new ItemProcessListener() {
        public void beforeProcess(Object o) {logger.info("Начало обработки");}
        public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
        public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
      })
      .listener(new ChunkListener() {
        public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
        public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
        public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
      })
      .build();
  }

  @Bean
  public Job convertBoardsJob() {
    return jobBuilderFactory.get("convertBoardsJob")
      .start(step1())
      .build();
  }
}
