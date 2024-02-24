package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Follower;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    
    Follower findById(int id);

    List<Follower> findByUser(User user);

    List<Follower> findByFollower(User user);
}