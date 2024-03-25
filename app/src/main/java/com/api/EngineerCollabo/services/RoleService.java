package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.ResponseRole;
import com.api.EngineerCollabo.entities.Role;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.RoleRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public Role createRole(String name, Integer countLog, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Role role = new Role();
        role.setName(name);
        role.setCountLog(countLog);
        role.setUserId(user.getId());

        return roleRepository.save(role);
    }

    public ResponseRole changeResponseRole(Role role) {
        ResponseRole responseRole = new ResponseRole();
        responseRole.setId(role.getId());
        responseRole.setName(role.getName());
        responseRole.setCountLog(role.getCountLog());
        responseRole.setUserId(role.getUser().getId());
        return responseRole;
    }

}