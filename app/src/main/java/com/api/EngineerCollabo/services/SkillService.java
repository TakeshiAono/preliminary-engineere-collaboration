package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.ResponseSkill;
import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.SkillRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    public Skill createSkill(String name, Integer countLog, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Skill skill = new Skill();
        skill.setName(name);
        skill.setCountLog(countLog);
        skill.setUserId(user.getId());

        return skillRepository.save(skill);
    }

    public ResponseSkill changeResponseSkill(Skill skill) {
        ResponseSkill responseSkill = new ResponseSkill();
        responseSkill.setId(skill.getId());
        responseSkill.setName(skill.getName());
        responseSkill.setCountLog(skill.getCountLog());
        responseSkill.setUserId(skill.getUser().getId());
        return responseSkill;
    }

}