package com.technoir.levelup.controller;

import com.technoir.levelup.dto.AdminUserDto;
import com.technoir.levelup.model.Role;
import com.technoir.levelup.model.User;
import com.technoir.levelup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @Value("${locale}")
    private String locale;

    private ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(locale != null ? locale : "ru"));

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin/users")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<AdminUserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(AdminUserDto.fromUser(user));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/user/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(value = "/user/markDelete/{id}")
    public ResponseEntity<String> setStatusDeleted(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        List<Role> roles = user.getRoles();
        List<String> result = new ArrayList<>();
        roles.forEach(role -> {
            if (role.getName().equals("ROLE_ADMIN")) {
                result.add(role.getName());
            }
        });
        if (result.contains("ROLE_ADMIN")) {
            return ResponseEntity.badRequest().body(bundle.getString("no_mark_admin_deleted"));
        } else {
            userService.markAsDeleted(id);
            return ResponseEntity.ok(AdminUserDto.fromUser(user).getStatus());
        }
    }

    @PatchMapping(value = "/user/setNotActive/{id}")
    public ResponseEntity<String> setStatusNotActive(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        List<Role> roles = user.getRoles();
        List<String> result = new ArrayList<>();
        roles.forEach(role -> {
            if (role.getName().equals("ROLE_ADMIN")) {
                result.add(role.getName());
            }
        });
        if (result.contains("ROLE_ADMIN")) {
            return ResponseEntity.badRequest().body(bundle.getString("no_mark_admin_noactive"));
        } else {
            userService.setNotActive(id);
            return ResponseEntity.ok(AdminUserDto.fromUser(user).getStatus());
        }
    }

}
