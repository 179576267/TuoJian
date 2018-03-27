package com.wzf.tuojian.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.tuojian.MyApplication;
import com.wzf.tuojian.R;
import com.wzf.tuojian.constant.UrlService;
import com.wzf.tuojian.function.http.ResponseSubscriber;
import com.wzf.tuojian.function.http.dto.request.CommunityListReqDto;
import com.wzf.tuojian.function.http.dto.response.BaseResponse;
import com.wzf.tuojian.function.http.dto.response.GameListResDto;
import com.wzf.tuojian.function.imageloader.ImageLoader;
import com.wzf.tuojian.ui.activity.GameDetailActivity;
import com.wzf.tuojian.ui.activity.GameFeedBackActivity;
import com.wzf.tuojian.ui.activity.SearchActivity;
import com.wzf.tuojian.ui.adapter.CommonAdapter;
import com.wzf.tuojian.ui.adapter.ViewHolder;
import com.wzf.tuojian.ui.base.BaseFragment;
import com.wzf.tuojian.ui.views.ScaleImageView;
import com.wzf.tuojian.ui.views.pullview.MultiColumnListView;
import com.wzf.tuojian.ui.views.pullview.internal.PLA_AdapterView;
import com.wzf.tuojian.utils.DebugLog;
import com.wzf.tuojian.utils.ScreenUtils;
import com.wzf.tuojian.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class GameFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    View mRootView;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.list)
    MultiColumnListView list;
    int page = 1;
    CommonAdapter<GameListResDto.WaterfallListBean> adapter;
    List<GameListResDto.WaterfallListBean> data;
    private boolean loadFinish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_game, null);
            ButterKnife.bind(this, mRootView);
            initData();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void initData() {
        initView();
        onRefresh();
    }

    private void initView() {
        tvCenter.setText("桌游百科");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.game_btn_mali_nor);
//        imRight1.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        srl.setOnRefreshListener(this);
        //实现首次自动显示加载提示
//        srl.post(new Runnable() {
//            @Override
//            public void run() {
//                srl.setRefreshing(true);
//            }
//        });
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        adapter = getAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                try {
                    GameDetailActivity.startMethod(bActivity, data.get(position).getBoardId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        list.setOnLoadMoreListener(new MultiColumnListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(loadFinish){
                    bActivity.showToast("已经全部加载完成");
                }else {
                    getData(false);//获取数据
                }

            }
        });

    }

    @Override
    public void onRefresh() {
        loadFinish = false;
        getData(true);
    }

    private void getData(final boolean refresh) {
        if (refresh) {
            page = 1;
        }
        CommunityListReqDto reqDto = new CommunityListReqDto();
        reqDto.setPageSize(30);
        reqDto.setPageNum(page);
        UrlService.SERVICE.getWaterfallList(reqDto.toEncodeString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<BaseResponse<GameListResDto>, BaseResponse<GameListResDto>>() {
                    @Override
                    public BaseResponse<GameListResDto> call(BaseResponse<GameListResDto> game) {
                        //加载异步线程获取图片的宽高
                        List<GameListResDto.WaterfallListBean> gameLists = game.getResponse().getWaterfallList();
                        for (GameListResDto.WaterfallListBean data : gameLists) {
                            if (data.getImgWidth() == 0 || data.getImgHeight() == 0) {
                                Bitmap bitmap = ImageLoader.getInstance().load(bActivity, data.getBoardImgUrl());
                                if (bitmap != null) {
                                    data.setImgWidth(bitmap.getWidth());
                                    data.setImgHeight(bitmap.getHeight());
                                }
                            }

                        }
                        return game;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseSubscriber<GameListResDto>(bActivity, true) {
                    @Override
                    public void onSuccess(GameListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page++;
                        if (refresh) {
                            data.clear();
                            srl.setRefreshing(false);
                        }
                        data.addAll(responseDto.getWaterfallList());
                        adapter.notifyDataSetChanged();
                        list.onLoadMoreComplete();
                        if (responseDto.getIsLastPage() == 1 || responseDto.getWaterfallList().size() == 0) {
                            loadFinish = true;
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        bActivity.showToast(message);
                        srl.setRefreshing(false);
                    }
                });
    }

    private CommonAdapter<GameListResDto.WaterfallListBean> getAdapter() {
        return new CommonAdapter<GameListResDto.WaterfallListBean>(data, bActivity, R.layout.item_game_list) {
            int w = ScreenUtils.getScreenWidth(MyApplication.getAppInstance()) / 2;
            @Override
            public void convert(ViewHolder viewHolder, GameListResDto.WaterfallListBean o) {
                DebugLog.i("GameFragment index :" + mDatas.indexOf(o) + ", w:"+ o.getImgWidth() + ", h:" + o.getImgHeight());
                ScaleImageView imageView = viewHolder.getView(R.id.im);
                imageView.setInitSize(o.getImgWidth(), o.getImgHeight());
                float scale = (float) o.getImgHeight() / (float) o.getImgWidth();
                int h = (int) (scale * w);
                ImageLoader.getInstance().displayTargeSizeImage(o.getBoardImgUrl(), imageView, w, h);
            }
        };
    }

    @OnClick({R.id.im_right1, R.id.rl_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_right1:
                startActivity(new Intent(bActivity, GameFeedBackActivity.class));
                break;
            case R.id.rl_search:
                SearchActivity.startMethod(bActivity, SearchActivity.SEARCH_GAME);
                break;
        }
    }

}
