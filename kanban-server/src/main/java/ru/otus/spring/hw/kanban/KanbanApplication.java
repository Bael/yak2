package ru.otus.spring.hw.kanban;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.hw.kanban.security.UserAccount;
import ru.otus.spring.hw.kanban.security.UserAccountRepository;

import javax.annotation.PostConstruct;


@SpringBootApplication(scanBasePackages = "ru.otus.spring.hw.kanban")
@EnableJpaRepositories(basePackages = {"ru.otus.spring.hw.kanban.repository", "ru.otus.spring.hw.kanban.security"})
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
public class KanbanApplication {

  public static void main(String[] args) {
    SpringApplication.run(KanbanApplication.class, args);

    HystrixCommand.Setter config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("boards"));

    HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
    properties.withExecutionTimeoutInMilliseconds(1000);
    properties.withCircuitBreakerSleepWindowInMilliseconds(4000);
    properties.withCircuitBreakerEnabled(true);
    config.andCommandPropertiesDefaults(properties);

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
