package com.gdzc.net.consts;

import android.content.SharedPreferences;

import com.gdzc.BuildConfig;
import com.gdzc.base.App;

import static com.gdzc.net.consts.HttpPath.SERVER;
import static com.gdzc.net.consts.HttpPath.SERVER_TEXT;


/**
 * Created by 王少岩 on 2016/9/14.
 */
public class HttpConsts {

    public static final String SP_SERVER = "sp_server";
    public static final String SP_SERVER_TEST = "sp_server_test";

    private static SharedPreferences getDefaultSharedPreferences() {
        return App.getAppContext().getPreferences();
    }

    /**
     * 设置服务地址
     *
     * @param server
     */
    public static void setServer(String server) {
        if (BuildConfig.DEBUG) {
            getDefaultSharedPreferences().edit().putString(SP_SERVER_TEST, server).commit();
        } else {
            getDefaultSharedPreferences().edit().putString(SP_SERVER, server).commit();
        }
    }

    public static String getServer() {
        if (BuildConfig.DEBUG) {
            return getDefaultSharedPreferences().getString(SP_SERVER_TEST, SERVER_TEXT);
        } else {
            return getDefaultSharedPreferences().getString(SP_SERVER, SERVER);
        }
    }
}
