package pl.klewandowski.gymapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.klewandowski.gymapp.model.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    String addUser(User user);

    void deleteUser(long id);

    User updateUser(long id, User user);

    User getUserById(long id);
}
