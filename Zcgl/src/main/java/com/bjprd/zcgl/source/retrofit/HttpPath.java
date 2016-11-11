package com.bjprd.zcgl.source.retrofit;

/**
 * Created by 王少岩 on 2016/9/19.
 */
public class HttpPath {
    /**
     * Url地址
     */
    public static final String SERVER = "http://exhm.b2bex.com/";//正式网地址
    public static final String SERVER_TEXT = "http://exhm.b2bex.com/";//测试网地址

    public static String getReqUrl(String url) {
        return HttpConsts.getServer() + url;
    }

    public static class UserUrl {
        public static final String appLoginUrl = "mobile/member/myMemCenter.api";
    }

}
