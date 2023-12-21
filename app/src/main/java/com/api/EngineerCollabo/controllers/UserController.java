package com.api.EngineerCollabo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserControllerクラス
 * ユーザ関連のAPI
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // サービスクラスの依存性注入
    @Autowired
    UserService userService;

    /**
     * ユーザ登録API
     * POST /user/regist
     * @param requestUserRegist ユーザ登録APIのリクエストボディ
     * @return responseUserRegist ユーザ登録APIのレスポンスボディ
     */
    @PostMapping("regist")
    public ResponseUserRegist userRegist(@RequestBody RequestUserRegist requestUserRegist) {

        // サービスクラスのユーザ登録処理呼び出し
        ResponseUserRegist responseUserRegist = userService.insertUser(requestUserRegist);

        // APIレスポンス
        return responseUserRegist;
    }

    /**
     +   * ログインAPI
     +   * POST /user/login
     +   * @param requestLogin ログインAPIのリクエストボディ
     +   * @return responseLogin ログインAPIのレスポンスボディ
     +   */
     @PostMapping("login")
     public ResponseLogin login(@RequestBody RequestLogin requestLogin) {

         // サービスクラスのログイン処理呼び出し
         ResponseLogin responseLogin = userService.login(requestLogin);

         // APIレスポンス
         return responseLogin;
        }
    }
