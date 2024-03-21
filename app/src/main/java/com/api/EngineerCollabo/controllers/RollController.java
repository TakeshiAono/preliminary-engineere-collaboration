package com.api.EngineerCollabo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.EngineerCollabo.entities.ResponseRoll;
import com.api.EngineerCollabo.entities.Roll;
import com.api.EngineerCollabo.repositories.RollRepository;
import com.api.EngineerCollabo.services.RollService;

@RestController
@RequestMapping("/rolls")
public class RollController {

    @Autowired
    RollRepository rollRepository;

    @Autowired
    RollService rollService;

    @PostMapping("/create")
    public void createRoll(@RequestBody Roll requestRoll) {
        String name = requestRoll.getName();
        Integer countLog = requestRoll.getCountLog();
        Integer userId = requestRoll.getUserId();

        if (userId != null) {
            rollService.createRoll(name, countLog, userId);
        }
    }

    @GetMapping("/{id}")
    public ResponseRoll responseRoll(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Roll roll = rollRepository.findById(id);
            return rollService.changeResponseRoll(roll);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putRoll(@PathVariable("id") Optional<Integer> ID, @RequestBody Roll requestRoll) {
        if (ID.isPresent()) {
            int id = ID.get();
            Roll roll = rollRepository.findById(id);

            String name = requestRoll.getName();
            if (name != null) {
                roll.setName(name);
            }

            Integer countLog = requestRoll.getCountLog();
            if (countLog != null) {
                roll.setCountLog(countLog);
            }

            rollRepository.save(roll);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            rollRepository.deleteById(id);
        }
    }
}
