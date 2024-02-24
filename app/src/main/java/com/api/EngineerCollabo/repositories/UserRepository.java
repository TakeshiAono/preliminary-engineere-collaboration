package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.Roll;
import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    User findByName(String name);

    List<User> findByEmail(String email);

    List<User> findByProject(Project project);

    User findByMessages(Message message);

    User findBySkills(Skill skill);

    User findByRolls(Roll roll);

    User findByChannels(Channel channel);

    List<User> findByOffers(Offer offer);

    List<User> findByScoutedOffers(Offer offer);
}