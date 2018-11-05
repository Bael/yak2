package ru.otus.spring.hw.kanban.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.exceptions.UserNameIsOccupiedException;

@Service
public class UserAccountDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UserAccountRepository userRepository;

    @Autowired
    public UserAccountDetailsService(PasswordEncoder encoder, UserAccountRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("username for log in " + username);
        UserAccount user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserAccountPrincipal(user);
    }

    public UserAccount createUser(UserAccount userAccount) {
        if (userRepository.findByUsername(userAccount.getUsername()) != null) {
            throw new UserNameIsOccupiedException(userAccount.getUsername());
        }

        UserAccount newUserAccount = new UserAccount(userAccount.getUsername(),
                encoder.encode(userAccount.getPassword()),

                userAccount.getRole());
        return userRepository.save(newUserAccount);

    }
}



