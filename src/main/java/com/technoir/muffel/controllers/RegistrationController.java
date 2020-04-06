package com.technoir.muffel.controllers;

import com.technoir.muffel.domain.Role;
import com.technoir.muffel.domain.User;
import com.technoir.muffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User newUser, Map<String, Object> model) {
        User user = userRepository.findByNickname(newUser.getNickname());
        if (user != null) {
            model.put("message", "User exists!!!!!!111");
            return "registration";
        }

//        newUser.setActive(true);

        if (newUser.getFirstname() == null && newUser.getLastname() == null) {
            newUser.setRoles(Collections.singleton(Role.ANONYMOUS));
        } else {
            newUser.setRoles(Collections.singleton(Role.REGULAR));
        }

        userRepository.save(newUser);
        return "redirect:/login";
    }
}
