package ru.otus.spring.hw.kanban.security;

import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

  UserAccount findByUsername(String username);
}

