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

import com.api.EngineerCollabo.entities.ResponseUserNotice;
import com.api.EngineerCollabo.entities.UserNotice;
import com.api.EngineerCollabo.repositories.UserNoticeRepository;
import com.api.EngineerCollabo.services.UserNoticeService;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/userNotices")
public class UserNoticeController {
    @Autowired
    UserNoticeRepository userNoticeRepository;

    @Autowired
    UserNoticeService userNoticeService;

    @PostMapping("/create")
    public void createUserNotice(@RequestBody UserNotice requestUserNotice) {
        String log = requestUserNotice.getLog();
        Integer userId = requestUserNotice.getUserId();
        Integer offerId = requestUserNotice.getOfferId();

        if (userId != null) {
            userNoticeService.createUserNotice(log, userId, offerId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserNotice> getUserNoticeById(@PathVariable("id") Integer id) {
        UserNotice userNotice = userNoticeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("UserNotice not found"));
        ResponseUserNotice response = new ResponseUserNotice(userNotice);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public void putUserNotice(@PathVariable("id") Optional<Integer> ID,
            @RequestBody UserNotice requestUserNotice) {
        if (ID.isPresent()) {
            int id = ID.get();
            UserNotice userNotice = userNoticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserNotice not found"));

            String log = requestUserNotice.getLog();
            if (log != null) {
                userNotice.setLog(log);
            }
            userNoticeRepository.save(userNotice);
        }
    }

    @DeleteMapping("{id}")
    public void deleteUserNotice(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            userNoticeRepository.deleteById(id);
        }
    }
}
