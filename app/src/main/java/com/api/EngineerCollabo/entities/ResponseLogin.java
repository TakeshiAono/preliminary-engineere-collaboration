package com.api.EngineerCollabo;

import lombok.Data;

@Data
public class ResponseLogin {
    // ステータス
    private String status;
    // ユーザID
    private Integer id;
    // ユーザ名
    private String name;
    // メールアドレス
    private String email;
}
