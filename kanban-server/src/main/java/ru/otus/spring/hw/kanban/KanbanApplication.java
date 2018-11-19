package ru.otus.spring.hw.kanban;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.hw.kanban.conversion.ToMongoBatchConfig;
import ru.otus.spring.hw.kanban.security.UserAccount;
import ru.otus.spring.hw.kanban.security.UserAccountRepository;

import javax.annotation.PostConstruct;


@SpringBootApplication(scanBasePackages = "ru.otus.spring.hw.kanban")
@EnableJpaRepositories(basePackages = {"ru.otus.spring.hw.kanban.repository", "ru.otus.spring.hw.kanban.security"})
@EnableMongoRepositories(basePackages = {"ru.otus.spring.hw.kanban.repository.mongo"})

@Configuration
@Import(ToMongoBatchConfig.class)
public class KanbanApplication {

  public static void main(String[] args) {
    SpringApplication.run(KanbanApplication.class, args);
  }


  @Autowired
  UserAccountRepository repository;

  @Autowired
  PasswordEncoder encoder;


  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job convertBoardsJob;

  public void start() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
    jobLauncher.run(convertBoardsJob, new JobParameters());
  }

  @PostConstruct
  public void initAdmin() {
    if (repository.findByUsername("admin") == null) {
      UserAccount acc = UserAccount.builder().username("admin")
                          .role("ADMIN")
                          .password(encoder.encode("admin"))
                          .build();
      repository.save(acc);
    }
    try {
      start();
    } catch (JobParametersInvalidException e) {
      e.printStackTrace();
    } catch (JobExecutionAlreadyRunningException e) {
      e.printStackTrace();
    } catch (JobRestartException e) {
      e.printStackTrace();
    } catch (JobInstanceAlreadyCompleteException e) {
      e.printStackTrace();
    }
  }
}
