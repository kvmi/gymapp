package pl.klewandowski.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.klewandowski.gymapp.model.User;
import pl.klewandowski.gymapp.repository.UserRepository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;

import static pl.klewandowski.gymapp.Constants.*;

@Primary
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Value("admin_password")
    private String password;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String addUser(User user) {
        if (!isValidEmailAddress(user.getEmail())) {
            return ADD_USER_WRONG_EMAIL_MESSAGE;
        }
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        try {
            userRepository.save(user);
            return ADD_USER_SUCCESS_MESSAGE;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ADD_USER_DUPLICATE_RECORD_IN_DB;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public User addAdmin() {
        if (userRepository.findUserByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(password));
            return userRepository.save(admin);
        }
        return null;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(long id, User user) {

        User userToUpdate = getUserById(id);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(s);
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
