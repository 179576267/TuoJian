package com.wzf.tuojian.function.http.dto.request;

import com.wzf.tuojian.utils.DebugLog;
import com.wzf.tuojian.utils.JsonUtils;
import com.wzf.tuojian.utils.MathUtilAndroid;

import java.io.Serializable;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-29 10:06
 */

public class BaseRequestDto implements Serializable{
    public String toEncodeString(){
        String json = JsonUtils.toJson(this);
        DebugLog.w("OKHTTP", " ——————————————————————————----->>>> before encode params <<<<--——————————————————————————————————---\n");
        DebugLog.d("OKHTTP", JsonUtils.format(json) + "\n");
        return MathUtilAndroid.encodeAES(json);
    }
}
