package com.bjprd.zcgl.base;

import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.binding.command.ReplyCommand;
import com.bjprd.zcgl.R;

/**
 * Created by 王少岩 on 2016/10/31.
 */

public class AppBarViewModel {
    private AppCompatActivity mActivity;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<Integer> navigation = new ObservableField<>();
    public final ObservableField<Integer> logo = new ObservableField<>();
    public final ObservableField<Integer> isShow = new ObservableField<>();

    public ReplyCommand naviCommon = new ReplyCommand(() -> {
        mActivity.finish();
    });

    public AppBarViewModel(AppCompatActivity activity, String str_title, boolean showLeft) {
        mActivity = activity;
        setTitle(str_title);
        setNavigation(showLeft ? R.mipmap.icon_back : 0);
        setLogo(0);
    }

    public void setTitle(String t) {
        title.set(t);
        setToolBarShow(TextUtils.isEmpty(t) ? false : true);
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
