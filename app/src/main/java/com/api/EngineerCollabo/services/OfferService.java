package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.OfferRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    public Offer createOffer(String message, Integer userId, Integer scoutedUserId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User scoutedUser = userRepository.findById(scoutedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Offer offer = new Offer();
        offer.setMessage(message);
        offer.setUserId(user.getId());
        offer.setScoutedUserId(scoutedUser.getId());

        return offerRepository.save(offer);
    }

}
