package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.ResponseUserNotice;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.UserNotice;
import com.api.EngineerCollabo.repositories.UserNoticeRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserNoticeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserNoticeRepository userNoticeRepository;

    public UserNotice createUserNotice(String log, Integer userId, Integer offerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserNotice userNotice = new UserNotice();
        userNotice.setLog(log);
        userNotice.setUserId(user.getId());
        userNotice.setOfferId(offerId);

        return userNoticeRepository.save(userNotice);
    }

    // オファー受信通知を作成する
    public void createOfferReceivedNotice(Integer userId, Integer offerId) {
        String log = "オファーが届きました";
        createUserNotice(log, userId, offerId);
    }

    public ResponseUserNotice changResponseUserNotice(UserNotice userNotice) {
        ResponseUserNotice responseUserNotice = new ResponseUserNotice();
        responseUserNotice.setId(userNotice.getId());
        responseUserNotice.setLog(userNotice.getLog());
        responseUserNotice.setUserId(userNotice.getUser().getId());
        return responseUserNotice;
    }
}