package ru.otus.spring.hw.kanban.security;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

  UserAccount findByUsername(String username);
  List<UserAccount> findAll();
}

