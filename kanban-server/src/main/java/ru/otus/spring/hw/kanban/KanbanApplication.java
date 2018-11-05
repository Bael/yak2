package ru.otus.spring.hw.kanban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.hw.kanban.security.UserAccount;
import ru.otus.spring.hw.kanban.security.UserAccountRepository;

import javax.annotation.PostConstruct;


@SpringBootApplication(scanBasePackages = "ru.otus.spring.hw.kanban")
@EnableJpaRepositories(basePackages = {"ru.otus.spring.hw.kanban.repository", "ru.otus.spring.hw.kanban.security"})
public class KanbanApplication {

  public static void main(String[] args) {
    SpringApplication.run(KanbanApplication.class, args);
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
