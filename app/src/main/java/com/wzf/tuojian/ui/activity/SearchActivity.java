package com.wzf.tuojian.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.tuojian.R;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.ui.fragments.SearchGameFragment;
import com.wzf.tuojian.ui.fragments.SearchPostFragment;
import com.wzf.tuojian.utils.SoftInputUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-04 11:23
 */

public class SearchActivity extends BaseActivity {
    public static final int SEARCH_POST = 1;
    public static final int SEARCH_GAME = 2;
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    private int searchType = SEARCH_POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        searchType = getIntent().getIntExtra("searchType", SEARCH_POST);
        initView();
        getSupportFragmentManager().beginTransaction().add(R.id.fm_container, searchType == SEARCH_POST ?
                new SearchPostFragment() : new SearchGameFragment()).commit();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("搜索" + (searchType == SEARCH_POST ? "帖子" : "桌游"));
        tvCenter.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.im_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        SoftInputUtil.closeSoftInput(this);
        super.onDestroy();
    }

    public static void startMethod(Context context, int  searchType) {
        context.startActivity(new Intent(context, SearchActivity.class).
                putExtra("searchType", searchType));
    }
}
