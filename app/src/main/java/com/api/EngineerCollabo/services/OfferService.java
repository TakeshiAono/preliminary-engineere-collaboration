package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.ResponseOffer;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.repositories.OfferRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.services.UserNoticeService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserNoticeService userNoticeService;

    public Offer createOffer(String message, Integer userId, Integer scoutedUserId, Integer projectId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User scoutedUser = userRepository.findById(scoutedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Offer offer = new Offer();
        offer.setMessage(message);
        offer.setUserId(user.getId());
        offer.setScoutedUserId(scoutedUser.getId());
        offer.setProjectId(project.getId());

        Offer savedOffer = offerRepository.save(offer);

        // オファーを受信したユーザーに通知を作成
        userNoticeService.createOfferReceivedNotice(scoutedUser.getId(), savedOffer.getId());

        return savedOffer;
    }

    public ResponseOffer changResponseOffer(Offer offer) {
        ResponseOffer responseOffer = new ResponseOffer();
        responseOffer.setId(offer.getId());
        responseOffer.setMessage(offer.getMessage());
        responseOffer.setUserId(offer.getUser().getId());
        responseOffer.setScoutedUserId(offer.getScoutedUser().getId());
        responseOffer.setProjectId(offer.getProject().getId());

        // プロジェクト名とユーザー名を設定
        responseOffer.setUserName(offer.getUser().getName());
        responseOffer.setProjectName(offer.getProject().getName());
        
        return responseOffer;
    }

}
