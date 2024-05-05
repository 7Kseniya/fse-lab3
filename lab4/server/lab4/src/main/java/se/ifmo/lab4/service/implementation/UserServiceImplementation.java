package se.ifmo.lab4.service.implementation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.ifmo.lab4.model.Role;
import se.ifmo.lab4.model.User;
import se.ifmo.lab4.repo.UserRepository;
import se.ifmo.lab4.service.UserService;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService{
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean authenticate(User user) {
        log.info("searching for user {}", user.getLogin());
        if(userRepository.existsById(user.getLogin())){
            if(passwordEncoder.matches(user.getPassword(), userRepository.findByLogin(user.getLogin()).getPassword())){
                log.info("logged in");
                return(true);
            }
            log.info("incorrect password");
            return false;
        }
        log.info("user {}: not found", user.getLogin());
        return false;
    }

    @Override
    public boolean register(User user) {
        log.info("registering user");
        if(userRepository.existsById(user.getLogin())){
            log.info("user already exists");
            return false;
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void delete(String login) {
        log.info("deleting user {}", login);
        userRepository.deleteByLogin(login);
    }
    
}
