package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.ResponseRole;
import com.api.EngineerCollabo.entities.Role;
import com.api.EngineerCollabo.repositories.RoleRepository;
import com.api.EngineerCollabo.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleControllerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResponseRoles() {
        Role role1 = new Role();
        role1.setName("Role1");
        Role role2 = new Role();
        role2.setName("Role2");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));
        when(roleService.changeResponseRole(role1)).thenReturn(new ResponseRole());
        when(roleService.changeResponseRole(role2)).thenReturn(new ResponseRole());

        List<ResponseRole> responseRoles = roleController.ResponseRoles();

        assertEquals(2, responseRoles.size());
    }

    @Test
    void testCreateRole() {
        Role role = new Role();
        role.setName("Role1");
        role.setCountLog(10);
        role.setUserId(1);

        roleController.createRole(role);

        verify(roleService, times(1)).createRole("Role1", 10, 1);
    }

    @Test
    void testResponseRole() {
        int roleId = 1;
        Role role = new Role();
        role.setId(roleId);

        when(roleRepository.findById(roleId)).thenReturn(role);
        when(roleService.changeResponseRole(any(Role.class))).thenReturn(new ResponseRole());

        ResponseRole response = roleController.responseRole(Optional.of(roleId));

        assertEquals(new ResponseRole(), response);
    }

    @Test
    void testPutRole() {
        int roleId = 1;
        Role existingRole = new Role();
        existingRole.setId(roleId);
        existingRole.setName("Old Role");

        Role updatedRole = new Role();
        updatedRole.setName("Updated Role");

        when(roleRepository.findById(roleId)).thenReturn(existingRole);

        roleController.putRole(Optional.of(roleId), updatedRole);

        verify(roleRepository, times(1)).save(existingRole);
        assertEquals("Updated Role", existingRole.getName());
    }

    @Test
    void testDeleteRole() {
        int roleId = 1;

        roleController.deleteOffer(Optional.of(roleId));

        verify(roleRepository, times(1)).deleteById(roleId);
    }
}
