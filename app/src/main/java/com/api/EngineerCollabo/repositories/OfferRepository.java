package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    
    Offer findById(int id);

    Offer findByMessage(String message);

    List<Offer> findByUser(User user);

    List<Offer> findByScoutedUser(User user);
}