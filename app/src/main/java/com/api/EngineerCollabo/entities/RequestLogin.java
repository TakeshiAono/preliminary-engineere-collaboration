package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class RequestLogin {
    // メールアドレス
    private String email;
    // パスワード
    private String password;
}
