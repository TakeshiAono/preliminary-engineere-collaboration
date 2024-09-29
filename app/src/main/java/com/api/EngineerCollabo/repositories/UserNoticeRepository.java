package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.UserNotice;

import java.util.List;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Integer> {

    List<UserNotice> findByUser(User user);
}