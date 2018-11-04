package ru.otus.spring.hw.kanban.security;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserAccountDetailsService implements UserDetailsService {

  @Autowired
  private UserAccountRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserAccount user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new UserAccountPrincipal(user);
  }


}



