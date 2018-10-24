package ru.otus.spring.hw.kanban.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsFilter implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {
    corsRegistry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("PUT", "POST", "GET", "DELETE", "OPTIONS")
      .maxAge(3600);
  }
}

