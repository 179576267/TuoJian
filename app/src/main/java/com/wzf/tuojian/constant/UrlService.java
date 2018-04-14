package com.wzf.tuojian.constant;

import com.wzf.tuojian.function.http.OkHttpUtils;
import com.wzf.tuojian.function.http.dto.request.GetSmsCodeReqDto;
import com.wzf.tuojian.function.http.dto.request.AccountRequestDto;
import com.wzf.tuojian.function.http.dto.response.BaseResponse;
import com.wzf.tuojian.function.http.dto.response.LoginResDto;
import com.wzf.tuojian.function.http.dto.response.QiNiuTokenResDto;
import com.wzf.tuojian.function.http.dto.response.UserInfoResDto;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 * @author wangzhenfei
 * @date 2017/2/28
 */

public interface UrlService {
    boolean DEBUG = true;
    String BASE_RESOURCE = "http://192.168.2.202:8081/boxingmanager/";
    String BASE_URL = "http://www.tuojianzx.com:8080/tuojian/";
    String LOGO_URL = "http://os7i4k6w5.bkt.clouddn.com/image/cxw/1506427655797.jpg";
    String DEFAULT_SHARE_URL = "www.mvoicer.com/share.html";

    UrlService SERVICE = OkHttpUtils.getInstance().getUrlService(UrlService.class);


    //==================================user=============================================
    //获取验证码
    @POST("user/getSmsCode")
    Observable<BaseResponse<Object>>  smsCode(@Body GetSmsCodeReqDto params);

    //注册
    @POST("user/userRegister")
    Observable<BaseResponse<Object>> register(@Body AccountRequestDto registerRequestDto);

    //修改密码接口
    @POST("user/changePwd")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> changePwd(@Field("params") String params);

    //登录
    @POST("user/userLogin")
    Observable<BaseResponse<LoginResDto>> login(@Body AccountRequestDto registerRequestDto);

    //退出登录接口
    @POST("user/exit")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> exit(@Field("params") String params);


    //获取用户信息
    @POST("user/getUserInformation")
    @FormUrlEncoded
    Observable<BaseResponse<UserInfoResDto>> getUserInformation(@Field("params") String params);

    //获取用户信息
    @POST("user/changeUserInformation")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> changeUserInformation(@Field("params") String params);

    //同步用户位置接口
    @POST("user/syncUserLocation")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> syncUserLocation(@Field("params") String params);

    //==================================system=============================================
    //获取七牛云的token
    @POST("system/getQiniuToken")
    @FormUrlEncoded
    Observable<BaseResponse<QiNiuTokenResDto>> getQiniuToken(@Field("params") String params);

}

