package com.api.EngineerCollabo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    User findByName(String name);

    List<User> findByEmail(String email);

    // List<User> findByProject(Project project);

    User findByMessages(Message message);

    User findBySkills(Skill skill);

    User findByRolls(Roll roll);

    User findByChannels(Channel channel);

    List<User> findByOffers(Offer offer);

    // List<User> findByScoutedOffers(Offer offer);
}