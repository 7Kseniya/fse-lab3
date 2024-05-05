package se.ifmo.lab4.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.ifmo.lab4.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByLogin(String login);
    void deleteByLogin(String login);
    boolean existsByPassword(String password);
    
}
