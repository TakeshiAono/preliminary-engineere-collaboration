package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.ResponseRoll;
import com.api.EngineerCollabo.entities.Roll;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.RollRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;

    public Roll createRoll(String name, Integer countLog, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Roll roll = new Roll();
        roll.setName(name);
        roll.setCountLog(countLog);
        roll.setUserId(user.getId());

        return rollRepository.save(roll);
    }

    public ResponseRoll changeResponseRoll(Roll roll) {
        ResponseRoll responseRoll = new ResponseRoll();
        responseRoll.setId(roll.getId());
        responseRoll.setName(roll.getName());
        responseRoll.setCountLog(roll.getCountLog());
        responseRoll.setUserId(roll.getUser().getId());
        return responseRoll;
    }

}