package com.gdzc.utils;


import com.gdzc.base.App;

/**
 * Created by 王少岩 on 2016/8/22.
 */
public class SPUtils {
    public static final String kUser_login = "login_flag";
    public static final String kUser_username = "username";
    public static final String kUser_userId = "userId";
    public static final String kUser_nickname = "nickname";
    public static final String kUser_dwbh = "dwbh";

    /**
     * 用户登录，保持必要的数据
     */
    public static void onLogin(String userId, String userName, String nickname, String dwbh) {
        saveString(kUser_userId, userId);
        saveString(kUser_username, userName);
        saveString(kUser_nickname, nickname);
        saveString(kUser_dwbh, dwbh);
        saveBoolean(kUser_login, true);
    }

    /**
     * 用户退出登录，清空数据
     */
    public static void onLoginOut() {
        App.getAppContext().getUserPreference().edit().clear().commit();
    }

    public static void saveString(String key, String value) {
        App.getAppContext().getUserPreference().edit().putString(key, value).commit();
    }

    public static void saveBoolean(String key, boolean value) {
        App.getAppContext().getUserPreference().edit().putBoolean(key, value).commit();
    }

    public static String getString(String key, String def) {
        return App.getAppContext().getUserPreference().getString(key, def);
    }

    public static boolean getBoolean(String key, boolean def) {
        return App.getAppContext().getUserPreference().getBoolean(key, def);
    }
}
