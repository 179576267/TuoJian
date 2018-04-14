package com.wzf.tuojian.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wzf.tuojian.R;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.AccountRequestDto;
import com.wzf.tuojian.function.http.dto.response.LoginResDto;
import com.wzf.tuojian.function.share.PlatformAuthorizeUserInfoManager;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.ui.model.UserInfo;
import com.wzf.tuojian.utils.REGX;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/2.
 */

public class LoginActivity extends BaseActivity {
//    @Bind(R.id.tv_center)
//    TextView tvCenter;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_psw)
    EditText etPsw;
//    @Bind(R.id.forget_psd)
//    TextView forgetPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
//        tvCenter.setText("登录");
//        tvCenter.setVisibility(View.VISIBLE);
        etPhone.setFilters(REGX.getFilters(REGX.REGX_MOBILE_INPUT));
//        forgetPsd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //中划线
        etPhone.setText(UserInfo.getInstance().getPhone());
        etPhone.setSelection(UserInfo.getInstance().getPhone().length());
        etPsw.setText(UserInfo.getInstance().getPsw());
        etPsw.setSelection(UserInfo.getInstance().getPsw().length());
    }

    @OnClick({R.id.to_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.forget_psd:
////                startActivity(new Intent(this, ForgetPwdActivity.class));
//                break;
            case R.id.to_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
//            case R.id.qq:
//                Platform platform = ShareSDK.getPlatform(QQ.NAME);
//                Platform.ShareParams shareParams = new  Platform.ShareParams();
//                shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
//                shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
//                shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
//                shareParams.setShareType(Platform.SHARE_WEBPAGE);
////                platform.setPlatformActionListener(platformActionListener);
//                platform.share(shareParams);
//                PlatformAuthorizeUserInfoManager platAuth = new PlatformAuthorizeUserInfoManager(this);
//                platAuth.qqShareAuthorize();
//                break;
//            case R.id.wx:
//                break;
//            case R.id.sina:
//                break;
        }
    }

    private void login() {
        final String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showToast("手机号码不正确");
            return;
        }
        final String pwd = etPsw.getText().toString();
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 20) {
            showToast("密码应该是6-20位");
            return;
        }
        AccountRequestDto dto = new AccountRequestDto();
        dto.setPwd(pwd);
        dto.setPhoneNum(phone);
        UrlService.SERVICE.login(dto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<LoginResDto>(this, true) {
                    @Override
                    public void onSuccess(LoginResDto loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        UserInfo.getInstance().setLogUser(loginResponseDto);
                        UserInfo.getInstance().setPhone(phone);
                        UserInfo.getInstance().setPsw(pwd);
                        showToast("登录成功");
//                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }


}
