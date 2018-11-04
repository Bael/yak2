package ru.otus.spring.hw.kanban.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserAccountDetailsService userAccountDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.
      csrf().disable()
      .authorizeRequests()
      .antMatchers("/**").permitAll()
//      .antMatchers("/login", "/", "/index.html", "/css/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userAccountDetailsService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }


  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }

}
