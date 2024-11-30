package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class ResponseUserNotice {
    private Integer id;

    private String log;

    private Integer userId;

    private Integer offerId;

    // 引数なしのコンストラクタ
    public ResponseUserNotice() {}

    // 引数ありのコンストラクタ
    public ResponseUserNotice(UserNotice userNotice) {
        this.id = userNotice.getId();
        this.log = userNotice.getLog();
        this.userId = userNotice.getUserId();
        this.offerId = userNotice.getOfferId();
    }

}