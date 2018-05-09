package com.wzf.tuojian.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzf.tuojian.R;

import butterknife.ButterKnife;

/**
 *
 * @author zhenfei.wang
 * @date 2016/9/9
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity bActivity;
    public boolean hidden;

    View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(getLayoutId(), null);
            ButterKnife.bind(this, mRootView);
            init();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    public abstract int getLayoutId();
    public abstract void init();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bActivity = (BaseActivity) context;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        this.hidden = hidden;
        if(hidden){
            onPause();
        }else {
            onResume();
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(bActivity == null){
            bActivity = (BaseActivity) getActivity();
        }
    }
}
