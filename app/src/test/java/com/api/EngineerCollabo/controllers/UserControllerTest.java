package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.*;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.UserService;
import com.api.EngineerCollabo.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController();
        userController.userService = userService;
        userController.userRepository = userRepository;
        userController.jwtUtil = jwtUtil;
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
        // モストデータの準備
        RequestLogin request = new RequestLogin();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        
        ResponseLogin response = new ResponseLogin();
        response.setStatus("success");  // "error"以外の値を設定
        response.setEmail("test@example.com");  // JWTトークン生成に必要
        
        // JwtUtilのモック追加
        when(jwtUtil.generateToken(anyString())).thenReturn("dummy-token");
        
        // モックの設定
        when(userService.login(any(RequestLogin.class))).thenReturn(response);
        
        // 実行
        ResponseEntity<?> result = userController.login(request);
        
        // 検証
        assertNotNull(result, "Response should not be null");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getHeaders().getFirst(HttpHeaders.SET_COOKIE), "Cookie should be set");
        assertEquals(response, result.getBody());
        
        // サービスメソッドが呼ばれたことを確認
        verify(userService, times(1)).login(any(RequestLogin.class));
        verify(jwtUtil, times(1)).generateToken(anyString());
    }

    @Test
    void testLoginError() {
        // テストデータの準備
        RequestLogin request = new RequestLogin();
        request.setEmail("test@example.com");
        request.setPassword("wrong-password");
        
        ResponseLogin response = new ResponseLogin();
        response.setStatus("error");
        
        // モックの設定
        when(userService.login(any(RequestLogin.class))).thenReturn(response);
        
        // 実行
        ResponseEntity<?> result = userController.login(request);
        
        // 検証
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        verify(userService, times(1)).login(any(RequestLogin.class));
    }

    @Test
    void testGetUser() {
        // モックの設定
        int userId = 1;
        User user = new User();
        user.setId(userId);
        ResponseUser responseUser = new ResponseUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userService.changeResponseUser(user)).thenReturn(responseUser);

        // 実行
        ResponseEntity<ResponseUser> result = userController.getUser(userId);

        // 検証
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseUser, result.getBody());
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
