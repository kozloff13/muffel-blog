package com.technoir.levelup.service.impl;

import com.technoir.levelup.model.Role;
import com.technoir.levelup.model.Status;
import com.technoir.levelup.model.User;
import com.technoir.levelup.repository.RoleRepository;
import com.technoir.levelup.repository.UserRepository;
import com.technoir.levelup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${locale}")
    private String locale;

    private ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(locale != null ? locale : "ru"));

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("user: {} registered successfully", registeredUser.getUsername()); //себе на заметку прокидывание переменной в лог

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("users: {} found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info(bundle.getString("user_was_found"), result.getUsername());
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if(result == null) {
            log.warn("user not found");
            return null;
        }
        log.info("User {} was found", result.getUsername());
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email);
        if(result == null) {
            log.warn("User with email: {} was found", email);
            return null;
        }
        log.info("User with email: {} was found", result.getEmail());
        return result;
    }

    @Override
    public void markAsDeleted(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            log.warn("user not found");
        } else {
            user.setStatus(Status.DELETED);
            userRepository.save(user);
            log.info("User with id={} mark as deleted", id);
        }
    }

    @Override
    public void setNotActive(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            log.warn("user not found");
        } else {
            user.setStatus(Status.NOT_ACTIVE);
            userRepository.save(user);
            log.info("User with id={} mark as not active", id);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("User with id={} deleted", id);
    }
}
