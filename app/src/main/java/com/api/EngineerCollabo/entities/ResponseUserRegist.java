package com.api.EngineerCollabo;

import lombok.Data;

@Data
public class ResponseUserRegist {
    // ユーザID
    private Integer id;
    // ユーザ名
    private String name;
    // メールアドレス
    private String email;
}
