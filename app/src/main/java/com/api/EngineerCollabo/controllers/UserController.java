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
    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin) {
        // サービスクラスのログイン処理呼び出し
        ResponseLogin responseLogin = userService.login(requestLogin);
        if (!responseLogin.getStatus().equals("error")) {
            // JWTトークンを生成
            String token = jwtUtil.generateToken(responseLogin.getEmail());
            
            // Cookieの作成
            ResponseCookie jwtCookie = ResponseCookie.from("jwt_token", token)
                .httpOnly(true)          // JavaScriptからアクセス不可
                .secure(true)            // HTTPS接続のみ
                .sameSite("Strict")      // CSRF対策
                .path("/")               // 全てのパスで利用可能
                .build();

            // ヘッダーにトークンを追加してレスポンス
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
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
