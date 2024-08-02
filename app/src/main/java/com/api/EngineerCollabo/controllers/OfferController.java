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
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;


import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.ResponseOffer;
import com.api.EngineerCollabo.repositories.OfferRepository;
import com.api.EngineerCollabo.services.OfferService;
import com.api.EngineerCollabo.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @PostMapping("/create")
public ResponseEntity<String> createOffer(@RequestBody Offer requestOffer) {
    try {
        String message = requestOffer.getMessage();
        Integer userId = requestOffer.getUserId();
        Integer scoutedUserId = requestOffer.getScoutedUserId();
        Integer projectId = requestOffer.getProjectId();

        if (userId != null && scoutedUserId != null) {
            offerService.createOffer(message, userId, scoutedUserId, projectId);
            return ResponseEntity.ok("Offer created successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid userId or scoutedUserId");
        }
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}

    @GetMapping("/{id}")
    public ResponseOffer responseOffer(@PathVariable("id") Integer id) {
        Offer offer = offerRepository.findById(id).orElse(null);
        if (offer != null) {
            return offerService.changResponseOffer(offer);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> putOffer(@PathVariable("id") Integer id, @RequestBody Offer requestOffer) {
        Offer offer = offerRepository.findById(id).orElse(null);
        if (offer != null) {
            String message = requestOffer.getMessage();
            if (message != null) {
                offer.setMessage(message);
                offerRepository.save(offer);
                return ResponseEntity.ok("Offer updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid message");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable("id") Integer id) {
        if (offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
            return ResponseEntity.ok("Offer deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
