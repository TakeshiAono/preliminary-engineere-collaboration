package com.api.EngineerCollabo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Role;
import com.api.EngineerCollabo.entities.User;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findById(int id);

    List<Role> findByUser(User user);
}