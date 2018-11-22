package ru.otus.spring.hw.kanban;

import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthIndicatorProperties;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.hw.kanban.security.UserAccount;
import ru.otus.spring.hw.kanban.security.UserAccountRepository;

import javax.annotation.PostConstruct;
import java.io.File;


@SpringBootApplication(scanBasePackages = "ru.otus.spring.hw.kanban")
@EnableJpaRepositories(basePackages = {"ru.otus.spring.hw.kanban.repository", "ru.otus.spring.hw.kanban.security"})
public class KanbanApplication {

  public static void main(String[] args) {
    SpringApplication.run(KanbanApplication.class, args);
  }

  @Bean
  JvmThreadMetrics threadMetrics(){
    return new JvmThreadMetrics();
  }

  @Bean
  DiskSpaceHealthIndicator diskSpaceHealthIndicator(
          @Value("${health.disk.filepath}") String filepath,
          @Value("${health.disk.threshold}") long threshold) {
    File path = new File(filepath);
    return new DiskSpaceHealthIndicator(path, threshold);
  }


  @Autowired
  UserAccountRepository repository;

  @Autowired
  PasswordEncoder encoder;

  @PostConstruct
  public void initAdmin() {
    if (repository.findByUsername("admin") == null) {
      UserAccount acc = UserAccount.builder().username("admin")
                          .role("ADMIN")
                          .password(encoder.encode("admin"))
                          .build();
      repository.save(acc);
    }
  }
}
