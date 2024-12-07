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
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import com.api.EngineerCollabo.entities.ResponseRole;
import com.api.EngineerCollabo.entities.Role;
import com.api.EngineerCollabo.repositories.RoleRepository;
import com.api.EngineerCollabo.services.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @GetMapping
    public List<ResponseRole> ResponseRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map((role) -> roleService.changeResponseRole(role)).toList();
    }

    @PostMapping("/create")
    public void createRole(@RequestBody Role requestRole) {
        String name = requestRole.getName();
        Integer countLog = requestRole.getCountLog();
        Integer userId = requestRole.getUserId();

        if (userId != null) {
            roleService.createRole(name, countLog, userId);
        }
    }

    @GetMapping("/{id}")
    public ResponseRole responseRole(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Role role = roleRepository.findById(id);
            return roleService.changeResponseRole(role);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putRole(@PathVariable("id") Optional<Integer> ID, @RequestBody Role requestRole) {
        if (ID.isPresent()) {
            int id = ID.get();
            Role role = roleRepository.findById(id);

            String name = requestRole.getName();
            if (name != null) {
                role.setName(name);
            }

            Integer countLog = requestRole.getCountLog();
            if (countLog != null) {
                role.setCountLog(countLog);
            }

            roleRepository.save(role);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            roleRepository.deleteById(id);
        }
    }
}
