package ru.otus.spring.hw.kanban.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw.kanban.security.UserAccount;
import ru.otus.spring.hw.kanban.security.UserAccountDetailsService;

import java.security.Principal;
import java.util.List;

@RestController
public class UserAccountController {

    private final UserAccountDetailsService service;

    @Autowired
    public UserAccountController(UserAccountDetailsService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('ADMIN')")
    public List<UserAccount> getUsers() {
      return service.getUsers();
    }

    @PostMapping("/user")
    public UserAccount createUser(@RequestBody UserAccount userAccount) {
        return service.createUser(userAccount);
    }

}
