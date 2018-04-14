package com.wzf.tuojian.function.http.dto.request;

/**
 * Created by wzf on 2017/6/28.
 */

public class AccountRequestDto extends BaseRequestDto{

    /**
     *{
     "phoneNum": "18351006261",
     "smsCode": "2960",
     "pwd": "111111"
     }
     */

    private String smsCode;
    private String pwd;
    private String phoneNum;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
