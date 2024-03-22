package com.api.EngineerCollabo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.entities.User;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Skill findById(int id);

    List<Skill> findByUser(User user);
}