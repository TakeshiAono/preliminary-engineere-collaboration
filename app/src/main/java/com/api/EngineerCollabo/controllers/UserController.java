package com.api.EngineerCollabo.controllers;

import java.util.Map;
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

    // ヘルスチェック用のエンドポイント
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

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

        Map<String, ResponseCookie> cookies = this.generateTokenCookies(responseUserRegist.getEmail());

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookies.get("access_token").toString())
            .header(HttpHeaders.SET_COOKIE, cookies.get("refresh_token").toString())
            .body(responseUserRegist);
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
            Map<String, ResponseCookie> cookies = this.generateTokenCookies(responseLogin.getEmail());

            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookies.get("access_token").toString())
                .header(HttpHeaders.SET_COOKIE, cookies.get("refresh_token").toString())
                .body(responseLogin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public Map<String, ResponseCookie> generateTokenCookies(String email) {
        // アクセストークンを生成
        String accessToken = jwtUtil.generateAccessToken(email);

        // リフレッシュトークンを生成
        String refreshToken = jwtUtil.generateRefreshToken(email);

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

        // クッキーをマップに格納して返す
        return Map.of(
            "access_token", accessTokenCookie,
            "refresh_token", refreshTokenCookie
        );
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

    @PutMapping("/users/{id}")
    public void putUser(@PathVariable("id") Optional<Integer> ID, @RequestBody User user) {
        int id = ID.get();
        userService.updateUser(id, user);
    }
}
