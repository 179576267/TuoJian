package com.wzf.tuojian.function.http.requestbody;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-03-02 14:51
 */

public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
