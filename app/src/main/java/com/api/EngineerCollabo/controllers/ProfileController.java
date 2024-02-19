package com.api.EngineerCollabo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.api.EngineerCollabo.User;
import com.api.EngineerCollabo.UserRepository;

@RestController
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profiles/{id}")
    public User getProfile(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            User user = userRepository.findById(id);
            return user;
        } else {
            return null;
        }
    }
}
