package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Integer> {

    UserNotice findById(int id);

    List<UserNotice> findByUser(User user);
}