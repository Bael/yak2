package ru.otus.spring.hw.kanban.security;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Data
@Builder
public class UserAccount {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String role;


}
