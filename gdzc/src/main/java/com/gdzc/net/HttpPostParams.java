package com.gdzc.net;


import com.gdzc.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 王少岩 on 2016/9/14.
 */
public class HttpPostParams {
    //通用类型请求参数
    public static Map<String, String> BaseParams() {
        Map<String, String> params = new HashMap<>();
        params.put("username", SPUtils.getString(SPUtils.kUser_username, ""));
        params.put("md5", MD5Tools.getMd5(SPUtils.getString(SPUtils.kUser_username, "")));
        return params;
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    public static Map<String, String> login(String username, String password) {
//        Map<String, String> map = BaseParams();
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    /**
     * 获取分类号
     *
     * @param flh
     * @param mc
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static Map<String, String> paramGetFlh(String flh, String mc, String pageNum, String pageSize) {
        Map<String, String> map = BaseParams();
        map.put("flh", flh);
        map.put("mc", mc);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return map;
    }

    /**
     *
     * @param flh
     * @param dj
     * @return
     */
    public static Map<String, String> paramGetTsxxUrl(String flh,String dj){
        Map<String, String> map = BaseParams();
        map.put("flh", flh);
        map.put("dj", dj);
        return map;
    }


}
