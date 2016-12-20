package com.gdzc.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ViewDataBinding;
import android.preference.PreferenceManager;


/**
 * Created by 王少岩 on 2016/8/16.
 */
public class App extends Application {
    private static App mInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences mUserPreference;
    private Activity mCurrentActivity;
    private ViewDataBinding mBinding;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //默认SP文件
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //用户相关SP文件
        mUserPreference = mInstance.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
    }

    public static App getAppContext() {
        return mInstance;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public SharedPreferences getUserPreference() {
        return mUserPreference;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        mCurrentActivity = currentActivity;
    }

    public void setCurrentBinding(ViewDataBinding binding){
        mBinding = binding;
    }

    public ViewDataBinding getCurrentBinding() {
        return mBinding;
    }
}
