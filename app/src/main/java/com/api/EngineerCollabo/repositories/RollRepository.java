package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RollRepository extends JpaRepository<Roll, Integer> {

    Roll findById(int id);

    List<Roll> findByUser(User user);
}