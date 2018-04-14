package com.wzf.tuojian.function.http.dto.request;

/**
 * Created by wzf on 2017/7/2.
 */

public class GetSmsCodeReqDto extends BaseRequestDto{
    public static final String SMS_CODE_REGISTER = "1";
    public static final String SMS_CODE_PSW = "2";
    private String phoneNum;
    private String codeType;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
