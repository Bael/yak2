package ru.otus.spring.hw.kanban.conversion;

//public class NoPersistenceBatchConfigurer {
//}

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class NoPersistenceBatchConfigurer extends DefaultBatchConfigurer {
  @Override
  public void setDataSource(DataSource dataSource) {
  }
}
