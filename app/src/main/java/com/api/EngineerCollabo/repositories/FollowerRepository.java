package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    
    Follower findById(int id);

    List<Follower> findByUser(User user);

    List<Follower> findByFollower(User user);
}