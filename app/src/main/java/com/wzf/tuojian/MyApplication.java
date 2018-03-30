package com.wzf.tuojian;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.imagepicker.ImagePicker;
import com.mob.MobApplication;
import com.tencent.bugly.Bugly;
import com.tendcloud.tenddata.TCAgent;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.imageloader.ImagePickerImageLoader;
import com.wzf.tuojian.function.map.BaiDuMapManager;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 09:15
 */

public class MyApplication extends MobApplication {
    private final String TAG = getClass().getSimpleName();
    private static MyApplication application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // bug捕获
        intBugly();
        initTalkingData();
        initImagePicker();
    }

    private void initTalkingData() {
        //数据统计
        if(UrlService.DEBUG){
            TCAgent.LOG_ON=true;
        }
        TCAgent.init(this);
    }

    //家里修改的的  对对对
    private void intBugly() {
        /*** Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        * 输出详细的Bugly SDK的Log；
        * 每一条Crash都会被立即上报；
        * 自定义日志将会在Logcat中输出。
        */

        if(UrlService.DEBUG){
            Bugly.init(getApplicationContext(), "ad28df98be", false);
        }else {
            Bugly.init(getApplicationContext(), "ad28df98be", false);
        }
        BaiDuMapManager.getInstance().getLocationMessage(null);
//        String ui = null;
//        ui.toString();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        //图片加载器
        imagePicker.setImageLoader(new ImagePickerImageLoader());
    }

    public  static  MyApplication getAppInstance(){
        return application;
    }
}
