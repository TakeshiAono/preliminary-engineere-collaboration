package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.Project;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    
    Offer findById(int id);

    Offer findByMessage(String message);

    List<Offer> findByUser(User user);

    List<Offer> findByScoutedUser(User user);

    List<Offer> findByProject(Project project);
}