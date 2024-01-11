package com.api.EngineerCollabo;

import lombok.Data;

@Data
public class RequestUserRegist {
    // ユーザ名
    // private String name;
    // パスワード
    private String password;
    // メールアドレス
    private String email;
}
