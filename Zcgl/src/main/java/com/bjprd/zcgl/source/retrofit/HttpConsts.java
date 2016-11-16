package com.bjprd.zcgl.source.retrofit;

import android.content.SharedPreferences;

import com.bjprd.zcgl.App;
import com.bjprd.zcgl.BuildConfig;

import static com.bjprd.zcgl.source.retrofit.HttpPath.SERVER;
import static com.bjprd.zcgl.source.retrofit.HttpPath.SERVER_TEXT;


/**
 * Created by 王少岩 on 2016/9/14.
 */
public class HttpConsts {

    public static final String SP_SERVER = "sp_server";
    public static final String SP_SERVER_TEST = "sp_server_test";

    public static final String REQ_PARAMS = "req_params";
    public static final int SCROLL_TIME = 5000;
    /**
     * token,time等死数据
     */
    public static final String kRequest_params_time = "time";
    public static final String kRequest_params_timeCheckValue = "timeCheckValue";
    public static final String kRequest_params_token = "token";
    public static final String kRequest_params_tokenCheckValue = "tokenCheckValue";
    public static final String kRequest_params_sourceType = "sourceType";
    public static final String kRequest_params_projectId = "projectId";
    public static final String PAGE_NO = "pageNo";
    public static final String kTime = "7cf1f0263ef39091dc48604aac8c8f9a";
    public static final String kToken = "d0468866ee36c1995563e8f8c6063a14";
    public static final String kSourceType_android = "2";//来自Android客户端
    public static final String kProjectId = "1026";//来自车福联盟
    /**
     * 默认分页条数
     */
    public static final int PAGE_SIZE = 15;

    public static String APPSP_LASTUSEDVERSION = "appsp_lastusedversion";
    public static String APPSP_HAVANEWVERSION = "appsp_havanewversion";

    public static class SplashReqType {
        public static final int ReqBase = 1000;
        public static final int ReqEnd = 1100;
        public static final int SPLASH_UPDATE = ReqBase + 1;
        public static final int SPLASH_ADBANNER = ReqBase + 2;
    }

    private static SharedPreferences getDefaultSharedPreferences() {
        return App.Companion.getAppContext().getPreferences();
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
