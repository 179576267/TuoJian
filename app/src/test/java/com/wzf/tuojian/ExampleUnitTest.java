package com.wzf.tuojian;

import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.GetDecorateManagerReqDto;
import com.wzf.tuojian.function.http.dto.response.GetDecorateManagerResDto;
import com.wzf.tuojian.utils.DebugLog;

import org.junit.Test;
import org.junit.runner.RunWith;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        UrlService.SERVICE.getDecorateManage(new GetDecorateManagerReqDto(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GetDecorateManagerResDto>(null, true) {
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