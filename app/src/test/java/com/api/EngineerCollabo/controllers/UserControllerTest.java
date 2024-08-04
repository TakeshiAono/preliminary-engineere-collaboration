package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.*;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserRegist() {
        // モックの設定
        RequestUserRegist request = new RequestUserRegist();
        ResponseUserRegist response = new ResponseUserRegist();

        when(userService.insertUser(request)).thenReturn(response);

        // 実行
        ResponseEntity<?> result = userController.userRegist(request);

        // 検証
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService, times(1)).insertUser(request);
    }

    @Test
    void testLogin() {
        // モックの設定
        RequestLogin request = new RequestLogin();
        ResponseLogin response = new ResponseLogin();
        response.setStatus("success");

        when(userService.login(request)).thenReturn(response);

        // 実行
        ResponseEntity<?> result = userController.login(request);

        // 検証
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService, times(1)).login(request);
    }

    @Test
    void testGetUser() {
        // モックの設定
        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(user);
        when(userService.changeResponseUser(user)).thenReturn(new ResponseUser());

        // 実行
        ResponseUser result = userController.getUser(Optional.of(userId));

        // 検証
        assertEquals(new ResponseUser(), result);
    }

    @Test
    void testDeleteUser() {
        // モックの設定
        int userId = 1;

        // 実行
        userController.deleteUser(Optional.of(userId));

        // 検証
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testPatchUser() {
        // モックの設定
        int userId = 1;
        User user = new User();

        // 実行
        userController.patchUser(Optional.of(userId), user);

        // 検証
        verify(userService, times(1)).updateUser(userId, user);
    }
}
