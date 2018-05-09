package com.wzf.tuojian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.wzf.tuojian.R;
import com.wzf.tuojian.ui.base.BaseActivity;
import com.wzf.tuojian.ui.fragments.DecorationManagerFragment;
import com.wzf.tuojian.ui.fragments.MallFragment;
import com.wzf.tuojian.ui.fragments.MeFragment;
import com.wzf.tuojian.ui.fragments.MessageFragment;
import com.wzf.tuojian.ui.fragments.PreDecorationFragment;
import com.wzf.tuojian.ui.views.NestRadioGroup;
import com.wzf.tuojian.ui.views.RedPoint;
import com.wzf.tuojian.utils.AppDeviceInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2018-05-02 11:19
 */
public class MenuActivity extends BaseActivity{
    public static final int EXTRA_VALUE_0 = 0;
    public static final int EXTRA_VALUE_1 = 1;
    public static final int EXTRA_VALUE_2 = 2;
    public static final int EXTRA_VALUE_3 = 3;
    public static final int EXTRA_VALUE_4 = 4;
    DecorationManagerFragment managerFragment;
    PreDecorationFragment preDecorationFragment;
    MessageFragment messageFragment;
    MallFragment mallFragment;
    MeFragment meFragment;
    private Fragment[] fragments;
    private int index;
    /**
     * 当前fragment的index
     */
    private int currentTabIndex;

    @Bind(R.id.redpoint_game)
    RedPoint redpointGame;
    @Bind(R.id.redpoint_me)
    RedPoint redpointMe;
    @Bind(R.id.main_radio)
    NestRadioGroup mainRadio;

    @Override
    public int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void init() {
        managerFragment = new DecorationManagerFragment();
        preDecorationFragment = new PreDecorationFragment();
        messageFragment = new MessageFragment();
        mallFragment = new MallFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{managerFragment, preDecorationFragment, messageFragment, mallFragment, meFragment};
        //默认加载
        getSupportFragmentManager().beginTransaction().add(R.id.fm_container, managerFragment).commit();
        mainRadio.check(R.id.radio_manager);
        currentTabIndex = EXTRA_VALUE_0;

        mainRadio.setOnCheckedChangeListener(new NestRadioGroup.OnSelectChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_manager:
                        index = EXTRA_VALUE_0;
                        break;
                    case R.id.radio_pre:
                        index = EXTRA_VALUE_1;
                        break;
                    case R.id.radio_message:
                        index = EXTRA_VALUE_2;
                        break;
                    case R.id.radio_mall:
                        index = EXTRA_VALUE_3;
                        break;
                    case R.id.radio_me:
                        index = EXTRA_VALUE_4;
                        break;
                    default:
                        index = 0;
                        break;
                }

                if (currentTabIndex != index) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(fragments[currentTabIndex]);
                    if (!fragments[index].isAdded()) {
                        trx.add(R.id.fm_container, fragments[index]);
                    }
                    trx.show(fragments[index]).commit();
                }
                currentTabIndex = index;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppDeviceInfo.goHome(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (index) {
            case EXTRA_VALUE_0:
                managerFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EXTRA_VALUE_1:
                preDecorationFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EXTRA_VALUE_2:
                messageFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EXTRA_VALUE_3:
                mallFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EXTRA_VALUE_4:
                meFragment.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }
}
