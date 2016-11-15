package com.bjprd.zcgl.zcdj;

import android.support.v7.app.AppCompatActivity;

import com.bjprd.zcgl.source.db.bean.TsxxBean;

/**
 * Created by 王少岩 on 2016/11/14.
 */

public class TsxxViewModel extends TsxxBean {
    private AppCompatActivity mActivity;

    public TsxxViewModel(AppCompatActivity activity) {
        mActivity = activity;
    }
}
