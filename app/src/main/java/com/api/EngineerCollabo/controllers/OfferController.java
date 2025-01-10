package com.api.EngineerCollabo.controllers;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

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
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseOffer;
import com.api.EngineerCollabo.entities.ResponseProject;
import com.api.EngineerCollabo.repositories.OfferRepository;
import com.api.EngineerCollabo.services.OfferService;
import com.api.EngineerCollabo.services.ProjectService;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferService offerService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<String> createOffer(@RequestBody Offer requestOffer) {
        System.out.println("Received offer message: " + requestOffer.getMessage());

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
    }
    
    @PostMapping("/accept/{id}")
    public ResponseEntity<Map<String, Object>> acceptOffer(@PathVariable("id") Integer offerId) {
        // オファーを受け入れる処理
        offerService.acceptOffer(offerId);
        
        // プロジェクトデータを取得
        Project project = projectService.getProjectByOfferId(offerId);
        
        // プロジェクトデータをResponseProjectに変換
        ResponseProject responseProject = projectService.changeResponseProject(project);
        
        // レスポンスデータを作成
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User successfully added to project");
        response.put("project", responseProject);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseOffer responseOffer(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Offer offer = offerRepository.findById(id);
            
            ResponseOffer responseOffer = offerService.changResponseOffer(offer);
            responseOffer.setIsAccepted(offer.getIsAccepted()); // isAcceptedをレスポンスに追加
            return responseOffer;
        } else {
            return null;
        }
    }

    @PatchMapping("{id}")
    public void putOffer(@PathVariable("id") Optional<Integer> ID, @RequestBody Offer requestOffer) {
        if (ID.isPresent()) {
            int id = ID.get();
            Offer offer = offerRepository.findById(id);

            String message = requestOffer.getMessage();
            if (message != null) {
                offer.setMessage(message);
            }

            offerRepository.save(offer);
        }
    }

    @DeleteMapping("{id}")
    public void deleteOffer(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            offerRepository.deleteById(id);
        }
    }

}
