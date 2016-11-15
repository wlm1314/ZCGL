package com.bjprd.zcgl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.bjprd.zcgl.utils.DB_Copy;

/**
 * Created by 王少岩 on 2016/8/16.
 */
public class App extends Application {
    private static App mInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences mUserPreference;
    private Activity mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserPreference = mInstance.getSharedPreferences("gdzc_user_pref", Context.MODE_PRIVATE);

        /**
         * 复制数据库
         */
        copyDB();
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

    public void setCurrentActivity(@NonNull Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
    /**
     * 复制数据库
     */
    private void copyDB() {
        DB_Copy db_copy = new DB_Copy(this);
        db_copy.copyDataBase();
    }
}
