package ru.otus.spring.hw.kanban.conversion;

import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import ru.otus.spring.hw.kanban.domain.Board;
import ru.otus.spring.hw.kanban.repository.BoardPagingRepository;
import ru.otus.spring.hw.kanban.repository.BoardRepository;
import ru.otus.spring.hw.kanban.repository.mongo.BoardMongoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  BoardPagingRepository boardPagingRepository;


  @Autowired
  BoardMongoRepository boardMongoRepository;

  public ItemReader<Board> reader() {
    RepositoryItemReader<Board> reader = new RepositoryItemReader<>();
    reader.setRepository(boardPagingRepository);
    reader.setMethodName("findAll");
    List parameters = new ArrayList();
    reader.setArguments(parameters);
    Map<String, Sort.Direction> sort = new HashMap<>();
    sort.put("id", Sort.Direction.ASC);
    reader.setSort(sort);
    return reader;
  }

  @Bean
  public ItemWriter<ru.otus.spring.hw.kanban.domain.mongo.Board> writer() {
    MongoItemWriter<ru.otus.spring.hw.kanban.domain.mongo.Board> writer = new MongoItemWriter<>();
    try {
      writer.setTemplate(mongoTemplate());
    } catch (Exception e) {
      logger.error(e.toString());
    }
    writer.setCollection("board");
    return writer;
  }


  @Bean
  public MongoDbFactory mongoDbFactory() throws Exception {
    return new SimpleMongoDbFactory(new MongoClient(), "kanban");
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
    return mongoTemplate;
  }


  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
      .<Board, ru.otus.spring.hw.kanban.domain.mongo.Board>chunk(5)
      .reader(reader())
      .processor(boardItemProcessor)
      .writer(writer())
      .listener(new ItemReadListener<Board>() {
        public void beforeRead() {
          logger.info("Начало чтения");
        }

        public void afterRead(Board o) {
          logger.info("Конец чтения " + o.toString());
        }

        public void onReadError(Exception e) {
          logger.info("Ошибка чтения");
        }
      })
      .listener(new ItemWriteListener() {
        public void beforeWrite(List list) {
          logger.info("Начало записи");
        }

        public void afterWrite(List list) {
          logger.info("Конец записи");
        }

        public void onWriteError(Exception e, List list) {
          logger.info("Ошибка записи");
        }
      })
      .listener(new ItemProcessListener() {
        public void beforeProcess(Object o) {
          logger.info("Начало обработки");
        }

        public void afterProcess(Object o, Object o2) {
          logger.info("Конец обработки");
        }

        public void onProcessError(Object o, Exception e) {
          logger.info("Ошбка обработки");
        }
      })
      .listener(new ChunkListener() {
        public void beforeChunk(ChunkContext chunkContext) {
          logger.info("Начало пачки");
        }

        public void afterChunk(ChunkContext chunkContext) {
          logger.info("Конец пачки");
        }

        public void afterChunkError(ChunkContext chunkContext) {
          logger.info("Ошибка пачки");
        }
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
