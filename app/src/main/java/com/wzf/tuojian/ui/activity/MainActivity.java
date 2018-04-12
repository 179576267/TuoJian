package com.wzf.tuojian.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wzf.tuojian.R;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.GetSmsCodeReqDto;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.utils.AppDeviceInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showToast(AppDeviceInfo.getNetworkType());

//        BaiDuMapManager.getInstance().getLocationMessage(new BaiDuMapManager.OnLocationMessageGetListener() {
//            @Override
//            public void onReceiveLocation(final String cityName, double lon, double lat) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(cityName);
//                    }
//                });
//            }
//        });

        GetSmsCodeReqDto reqDto = new GetSmsCodeReqDto();
        reqDto.setUserMobile("18521709590");
        reqDto.setCodeType(GetSmsCodeReqDto.SMS_CODE_REGISTER);
        UrlService.SERVICE.smsCode(reqDto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        showToast("验证码已发送");
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });

//        RegisterRequestDto dto = new RegisterRequestDto();
//        dto.setUserId("123");
//        dto.setNickname("wzf");
//        dto.setSmsCode("1234");
//        dto.setUserPwd("qqqqqq");
//        dto.setUserMobile("18521709590");
//        UrlService.SERVICE.register(dto.toEncodeString())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new ResponseSubscriber<Object>(this, true) {
//                    @Override
//                    public void onSuccess(Object loginResponseDto) throws Exception {
//                        super.onSuccess(loginResponseDto);
//                        tv.setText(loginResponseDto.toString());
//                    }
////
//                    @Override
//                    public void onFailure(int code, String message) throws Exception {
//                        super.onFailure(code, message);
//                        showToast(message);
//                    }
//                });
    }
}
