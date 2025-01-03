package com.api.EngineerCollabo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import java.time.Duration;

import com.api.EngineerCollabo.entities.RequestLogin;
import com.api.EngineerCollabo.entities.RequestUserRegist;
import com.api.EngineerCollabo.entities.ResponseLogin;
import com.api.EngineerCollabo.entities.ResponseUserRegist;
import com.api.EngineerCollabo.entities.ResponseUser;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.UserService;
import com.api.EngineerCollabo.util.JwtUtil;

/**
 * UserControllerクラス
 * ユーザ関連のAPI
 */
@RestController
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class UserController {

    // サービスクラスの依存性注入
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtUtil jwtUtil;

    /**
     * ユーザ登録API
     * POST /user/regist
     * 
     * @param requestUserRegist ユーザ登録APIのリクエストボディ
     * @return responseUserRegist ユーザ登録APIのレスポンスボディ
     */
    @PostMapping("/account")
    public ResponseEntity<?> userRegist(@RequestBody RequestUserRegist requestUserRegist) {
        ResponseUserRegist responseUserRegist = null;
        // サービスクラスのユーザ登録処理呼び出し
        try {
            responseUserRegist = userService.insertUser(requestUserRegist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // APIレスポンス
        return ResponseEntity.ok(responseUserRegist);
    }

    /**
     * ログインAPI
     * POST /user/login
     * 
     * @param requestLogin ログインAPIのリクエストボディ
     * @return responseLogin ログインAPIのレスポンスボディ
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin request) {
        ResponseLogin responseLogin = userService.login(request);
        
        if (!responseLogin.getStatus().equals("error")) {
            // アクセストークンとリフレッシュトークンを生成
            String accessToken = jwtUtil.generateAccessToken(responseLogin.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(responseLogin.getEmail());
            
            // アクセストークンのクッキー
            ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMillis(jwtUtil.getExpirationTime()))
                .build();
            
            // リフレッシュトークンのクッキー
            ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMillis(jwtUtil.getRefreshExpirationTime()))
                .build();

            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(responseLogin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /* profile api */
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("id") Integer id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ResponseUser responseUser = userService.changeResponseUser(user);
            
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            userRepository.deleteById(id);
        }
    }

    @PatchMapping("/users/{id}")
    public void patchUser(@PathVariable("id") Optional<Integer> ID, @RequestBody User user) {
        int id = ID.get();
        userService.updateUser(id, user);
    }

}
