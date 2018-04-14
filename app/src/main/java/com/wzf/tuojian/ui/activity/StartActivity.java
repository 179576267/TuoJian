package com.wzf.tuojian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;


import com.wzf.tuojian.R;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.AccountRequestDto;
import com.wzf.tuojian.function.http.dto.response.LoginResDto;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.ui.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-03-14 14:59
 */

public class StartActivity extends BaseActivity {
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 2000;
    private boolean loginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_start_page);
        init();
    }

    private void init() {
//        Glide.with(this).load(R.mipmap.start_page).asGif().into(im);
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                nextActivity();
            }
        }, SPLASH_DELAY_MILLIS);
        login();
    }

    private void login() {
        String accountNum = UserInfo.getInstance().getPhone();
        String psw = UserInfo.getInstance().getPsw();
        if (TextUtils.isEmpty(accountNum)) {
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            return;
        }

        AccountRequestDto dto = new AccountRequestDto();
        dto.setPwd(psw);
        dto.setPhoneNum(accountNum);
        UrlService.SERVICE.login(dto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<LoginResDto>() {
                    @Override
                    public void onSuccess(LoginResDto loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        UserInfo.getInstance().setLogUser(loginResponseDto);
//                        startActivity(new Intent(StartActivity.this, MenuActivity.class));
//                        finish();
                        loginSuccess = true;
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });

    }

    private void nextActivity() {
        if(loginSuccess){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else {
            UserInfo.isLogin(this);
            finish();
        }
        finish();
    }
}
