package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Roll;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface RollRepository extends JpaRepository<Roll, Integer> {

    Roll findById(int id);

    List<Roll> findByUser(User user);
}