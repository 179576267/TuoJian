package com.wzf.tuojian.utils;

import android.util.Log;
import android.widget.Toast;

import com.wzf.tuojian.MyApplication;
import com.wzf.tuojian.constant.UrlService;

/**
 * zhenfei.wang
 * 调试打印信息
 */

public class DebugLog {

    public static void v(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.v(tag, getContent(msg));
        }
    }

    public static void d(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.d(tag, getContent(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.i(tag, getContent(msg));
        }
    }

    public static void i(String msg) {
        if (UrlService.DEBUG) {
            Log.i("DEBUG", getContent(msg));
        }
    }

    public static void w(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.w(tag, getContent(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.e(tag, getContent(msg));
        }
    }

    /**
     * 在开发阶段的测试toast
     * @param s
     */
    public static void toast(String s){
        if(UrlService.DEBUG){
            Toast.makeText(MyApplication.getAppInstance().getApplicationContext(),
                    s,
                    Toast.LENGTH_LONG).show();
        }
    }


    private static String getContent(Object content)
    {
        StackTraceElement element = null;
        try {
            element = Thread.currentThread().getStackTrace()[4];
            String[] clsNames = element.getClassName().split("\\.");
            return clsNames[clsNames.length-1] + ":" + element.getLineNumber() +  " [" + element.getMethodName() + "] -> " + content;
        } catch (Exception e) {
            return "";
        }
    }

}
