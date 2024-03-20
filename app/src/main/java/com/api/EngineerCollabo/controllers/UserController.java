package com.api.EngineerCollabo.controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.EngineerCollabo.entities.RequestLogin;
import com.api.EngineerCollabo.entities.RequestUserRegist;
import com.api.EngineerCollabo.entities.ResponseLogin;
import com.api.EngineerCollabo.entities.ResponseUserRegist;
import com.api.EngineerCollabo.entities.ResponseUser;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.UserService;

/**
 * UserControllerクラス
 * ユーザ関連のAPI
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    // サービスクラスの依存性注入
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

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
            // TODO: handle exception
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
    public ResponseEntity<?>  login(@RequestBody RequestLogin requestLogin) {
        // サービスクラスのログイン処理呼び出し
        ResponseLogin responseLogin = userService.login(requestLogin);
        // APIレスポンス
        if(responseLogin.getStatus() != "error") {
            return ResponseEntity.ok(responseLogin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /* profile api */
    @GetMapping("/users/{id}")
    public ResponseUser getUser(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            User user = userRepository.findById(id);
            return userService.changeResponseUser(user);
        } else {
            return null;
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
