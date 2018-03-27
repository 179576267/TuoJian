package com.wzf.tuojian.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.wzf.tuojian.MyApplication;
import com.wzf.tuojian.R;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.utils.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @Description: 分享的封装
 * @author: wangzhenfei
 * @date: 2017-05-10 18:35
 */

public class ShareDialog extends Dialog {
    private Platform.ShareParams shareParams;
    public ShareDialog(BaseActivity context,String imgUrl, String title, String content
            , String targetUrl) {
        super(context, R.style.ShareDialogTheme);
        shareParams = new  Platform.ShareParams();
        shareParams.setText(content);
        shareParams.setTitle(title);
        shareParams.setImageUrl(imgUrl);
        shareParams.setTitleUrl(targetUrl);//qq用
        shareParams.setUrl(targetUrl);//微信用
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setWindowAnimations(R.style.ShareDialogTheme); // 添加动画
        window.requestFeature(Window.FEATURE_NO_TITLE);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.dialog_share);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.width = ScreenUtils.getScreenWidth(MyApplication.getAppInstance());
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_wechat, R.id.ll_friend_circle, R.id.ll_qq, R.id.ll_qzone, R.id.ll_sina, R.id.tv_cancel})
    public void onClick(View view) {
        Platform platform;
        switch (view.getId()) {
            case R.id.ll_wechat:
                platform = ShareSDK.getPlatform(Wechat.NAME);
                platform.share(shareParams);
                break;
            case R.id.ll_friend_circle:
                platform = ShareSDK.getPlatform(WechatMoments.NAME);
                shareParams.setTitle(shareParams.getText());
                platform.share(shareParams);
                break;
            case R.id.ll_qq:
                platform = ShareSDK.getPlatform(QQ.NAME);
                platform.share(shareParams);
                break;
            case R.id.ll_qzone:
                platform = ShareSDK.getPlatform(QZone.NAME);
                platform.share(shareParams);
                break;
            case R.id.ll_sina:
                platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                platform.share(shareParams);
                break;
            case R.id.tv_cancel:
                break;
        }
        dismiss();
    }
}
