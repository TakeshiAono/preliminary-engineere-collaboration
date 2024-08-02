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
import com.api.EngineerCollabo.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfferService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OfferRepository offerRepository;

    public Offer createOffer(String message, Integer userId, Integer scoutedUserId, Integer projectId) {
        // ユーザーとプロジェクトの取得
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User scoutedUser = userRepository.findById(scoutedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // ユーザーがプロジェクトに参加しているかチェック
        if (!userService.isUserPartOfProject(userId, projectId)) {
            throw new IllegalArgumentException("User is not part of the project");
        }

        // オファーの作成
        Offer offer = new Offer();
        offer.setMessage(message);
        offer.setUserId(user.getId());
        offer.setScoutedUserId(scoutedUser.getId());
        offer.setProjectId(project.getId());

        // オファーの保存
        return offerRepository.save(offer);
    }

    public ResponseOffer changResponseOffer(Offer offer) {
        ResponseOffer responseOffer = new ResponseOffer();
        responseOffer.setId(offer.getId());
        responseOffer.setMessage(offer.getMessage());
        responseOffer.setUserId(offer.getUser().getId());
        responseOffer.setScoutedUserId(offer.getScoutedUser().getId());
        responseOffer.setProjectId(offer.getProject().getId());
        return responseOffer;
    }
}
