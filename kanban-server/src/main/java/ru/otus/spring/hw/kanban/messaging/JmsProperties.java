package ru.otus.spring.hw.kanban.messaging;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JmsProperties {

  @Value("${jms.queue}")
  private String jmsQueueName;

  @Value("${jms.task-queue}")
  private String jmsTaskQueueName;

  @Value("${jms.task-reply-queue}")
  private String jmsTaskReplyQueueName;


}
