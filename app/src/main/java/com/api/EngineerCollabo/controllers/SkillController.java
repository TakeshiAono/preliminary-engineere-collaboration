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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import com.api.EngineerCollabo.entities.ResponseSkill;
import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.repositories.SkillRepository;
import com.api.EngineerCollabo.services.SkillService;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillService skillService;

    @GetMapping
    public List<ResponseSkill> ResponseSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream().map((skill) -> skillService.changeResponseSkill(skill)).toList();
    }

    @PostMapping("/create")
    public void createSkill(@RequestBody Skill requestSkill) {
        String name = requestSkill.getName();
        Integer countLog = requestSkill.getCountLog();
        Integer userId = requestSkill.getUserId();

        if (userId != null) {
            skillService.createSkill(name, countLog, userId);
        }
    }

    @GetMapping("/{id}")
    public ResponseSkill responseSkill(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Skill skill = skillRepository.findById(id);
            return skillService.changeResponseSkill(skill);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putSkill(@PathVariable("id") Optional<Integer> ID, @RequestBody Skill requestSkill) {
        if (ID.isPresent()) {
            int id = ID.get();
            Skill Skill = skillRepository.findById(id);

            String name = requestSkill.getName();
            if (name != null) {
                Skill.setName(name);
            }

            Integer countLog = requestSkill.getCountLog();
            if (countLog != null) {
                Skill.setCountLog(countLog);
            }

            skillRepository.save(Skill);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            skillRepository.deleteById(id);
        }
    }
}
