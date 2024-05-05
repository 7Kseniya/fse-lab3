package se.ifmo.lab4.service;

import se.ifmo.lab4.model.User;

public interface UserService{
    boolean authenticate(User user);
    boolean register(User user);
    void delete(String login);
    
} 