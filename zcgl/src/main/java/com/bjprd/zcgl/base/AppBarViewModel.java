package com.bjprd.zcgl.base;

import android.databinding.ObservableField;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.binding.command.ReplyCommand;

/**
 * Created by 王少岩 on 2016/10/31.
 */

public class AppBarViewModel {
    private FragmentActivity mActivity;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<Integer> navigation = new ObservableField<>();
    public final ObservableField<Integer> logo = new ObservableField<>();
    public final ObservableField<Integer> isShow = new ObservableField<>();

    public ReplyCommand naviCommon = new ReplyCommand(() -> {
        mActivity.finish();
    });

    public AppBarViewModel(FragmentActivity activity) {
        mActivity = activity;
        setTitle(null);
        setNavigation(0);
        setLogo(0);
        setToolBarShow(false);
    }

    public void setTitle(String t){
        title.set(t);
        setToolBarShow(true);
    }

    public void setNavigation(int resId) {
        navigation.set(resId);
    }

    public void setLogo(int resId) {
        logo.set(resId);
    }

    public void setToolBarShow(boolean show) {
        isShow.set(show ? View.VISIBLE : View.GONE);
    }

}
