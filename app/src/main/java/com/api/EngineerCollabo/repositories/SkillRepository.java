package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Roll;
import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Skill findById(int id);

    List<Roll> findByUser(User user);
}