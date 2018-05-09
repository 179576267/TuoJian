package com.wzf.tuojian.function.http.dto.request;

import com.wzf.tuojian.ui.model.UserInfo;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2018-05-02 12:29
 */

public class GetDecorateManagerReqDto extends BaseRequestDto {

    /**
     * token : 201803222306461869dbbe2e16a5b4c24b20eb9af049014b9
     * userId : 20180322201633201052dac987bee452e84c77814e2266935
     * udId : 20180325230916019ed931ed89b7f44dd8de86e25d29c0124
     */

    private String token;
    private String userId;
    private String udId;

    public GetDecorateManagerReqDto(String udId) {
        this.userId = UserInfo.getInstance().getUid();
        this.token = UserInfo.getInstance().getToken();
        this.udId = udId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUdId() {
        return udId;
    }

    public void setUdId(String udId) {
        this.udId = udId;
    }
}
