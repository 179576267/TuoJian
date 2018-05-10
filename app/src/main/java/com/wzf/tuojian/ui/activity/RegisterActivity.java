package com.wzf.tuojian.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wzf.tuojian.R;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.CountTimeDownManager;
import com.wzf.tuojian.function.http.OkHttpUtils;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.GetSmsCodeReqDto;
import com.wzf.tuojian.function.http.dto.request.AccountReqDto;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.utils.REGX;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * @author wzf
 * @date 2017/7/2
 */

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }



    @Override
    protected void init() {
        etPhone.setFilters(REGX.getFilters(REGX.REGX_MOBILE_INPUT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        CountTimeDownManager.setListener(new CountTimeDownManager.TimeCountDownListener() {
            @Override
            public void time(int count) {
                if(count == 0){
                    btnGetCode.setText("获取验证码");
                    btnGetCode.setClickable(true);
                }else {
                    btnGetCode.setText(count + "");
                    btnGetCode.setClickable(false);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountTimeDownManager.stop();
    }

    @OnClick({R.id.im_left, R.id.btn_get_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.btn_get_code:
                getSmsCode();
                //dev master
                break;
            case R.id.btn_login:
                register();
                break;
            default:
                break;
        }
    }

    private void getSmsCode() {
        String phone = etPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            showToast("手机号码不正确");
            return;
        }
        CountTimeDownManager.start(60);
        GetSmsCodeReqDto reqDto = new GetSmsCodeReqDto();
        reqDto.setPhoneNum(phone);
        reqDto.setCodeType(GetSmsCodeReqDto.SMS_CODE_REGISTER);
        OkHttpUtils.getInstance().getUrlService(UrlService.class).smsCode(reqDto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        showToast("验证码已发送");
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });

    }

    private void register() {
        String phone = etPhone.getText().toString();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            showToast("手机号码不正确");
            return;
        }
        String smsCode = etSmsCode.getText().toString();
        if(TextUtils.isEmpty(smsCode)){
            showToast("验证码不能为空");
            return;
        }
//        String nickName = etNickname.getText().toString();
//        if(TextUtils.isEmpty(nickName)){
//            showToast("昵称不能为空");
//            return;
//        }
//        if(nickName.length() > 15){
//            showToast("昵称不能超过15个字符");
//            return;
//        }
        String pwd = etPsw.getText().toString();
        if(TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 20){
            showToast("密码应该是6-20位");
            return;
        }
        AccountReqDto dto = new AccountReqDto();
        dto.setSmsCode(smsCode);
        dto.setPwd(pwd);
        dto.setPhoneNum(phone);
        UrlService.SERVICE.register(dto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
//                        TCAgent.onRegister(loginResponseDto.getUid(), TDAccount.AccountType.TYPE1, registerDto.getNickName());
                        showToast("注册成功");
                        finish();
                    }
//
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

}
