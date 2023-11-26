package com.api.EngineerCollabo;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    public List<List<User>> getScoutedUser(User user) {
      List<Offer> offers = offerRepository.findByUser(user);
      List<List<User>> users = new ArrayList<>();
      offers.forEach(offer -> {
        System.out.println("aaa");
        System.out.println(offer);
        users.add(userRepository.findByScoutedOffers(offer));
      });
      return users;
    }
}