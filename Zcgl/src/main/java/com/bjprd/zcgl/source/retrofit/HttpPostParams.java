package com.bjprd.zcgl.source.retrofit;


import com.bjprd.zcgl.utils.SPUtils;
import com.bjprd.zcgl.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 王少岩 on 2016/9/14.
 */
public class HttpPostParams {
    private static HttpPostParams httpPostParams;
    private HashMap<String, String> params;

    public Map<String, ByteArrayInputStream> mapImages; //存图片的
    public Map<String, File> mapFiles; //待上传的文件

    public static HttpPostParams getInstance() {
        if (httpPostParams == null) {
            httpPostParams = new HttpPostParams();
        }
        return httpPostParams;
    }

    //通用类型请求参数
    public Map<String, String> BaseParams() {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.clear();
        String time = System.currentTimeMillis() + "";
        String timeCheckValue = Utils.md5(time + HttpConsts.kTime);
        params.put(HttpConsts.kRequest_params_time, time);
        params.put(HttpConsts.kRequest_params_timeCheckValue, timeCheckValue);
        params.put(HttpConsts.kRequest_params_sourceType, HttpConsts.kSourceType_android);
        params.put(HttpConsts.kRequest_params_projectId, HttpConsts.kProjectId);
        return params;
    }

    private void autoAddToken() {
        String token = SPUtils.getUserToken();
        try {
            String tokenCheckValue = Utils.md5(token + HttpConsts.kToken);
            params.put(HttpConsts.kRequest_params_token, token);
            params.put(HttpConsts.kRequest_params_tokenCheckValue, tokenCheckValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autoAddPageSize() {
        params.put("pageSize", HttpConsts.PAGE_SIZE + "");
    }

    public void autoAddPageSize(int pageSize) {
        params.put("pageSize", pageSize + "");
    }

    /**
     * 登陆
     *
     * @param account
     * @param password
     * @return
     */
    public static Map<String, String> login(String account, String password) {
//        Map<String, String> map = BaseParams();
        Map<String, String> map = new HashMap<>();
        map.put("loginName", account);
        map.put("identifyingCode", password);
        return map;
    }

}
