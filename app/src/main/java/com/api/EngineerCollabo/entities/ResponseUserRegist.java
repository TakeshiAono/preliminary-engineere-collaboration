package com.api.EngineerCollabo.entities;

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
