package com.wzf.tuojian.ui.fragments;

import android.content.Intent;

import com.wzf.tuojian.R;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.GetDecorateManagerReqDto;
import com.wzf.tuojian.function.http.dto.response.GetDecorateManagerResDto;
import com.wzf.tuojian.function.http.dto.response.LoginResDto;
import com.wzf.tuojian.ui.activity.LoginActivity;
import com.wzf.tuojian.ui.activity.MenuActivity;
import com.wzf.tuojian.ui.base.BaseFragment;
import com.wzf.tuojian.ui.model.UserInfo;
import com.wzf.tuojian.utils.DebugLog;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * @author wzf
 * @date 2017/7/5
 */

public class DecorationManagerFragment extends BaseFragment{

    @Override
    public int getLayoutId() {
        return R.layout.fragment_decoration_manager;
    }
    @Override
    public void init() {
        UrlService.SERVICE.getDecorateManage(new GetDecorateManagerReqDto(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GetDecorateManagerResDto>(bActivity, true) {
                    @Override
                    public void onSuccess(GetDecorateManagerResDto dto) throws Exception {
                        DebugLog.e("DecorationManagerFragment", dto.toString());
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {

                    }
                });
    }


}
