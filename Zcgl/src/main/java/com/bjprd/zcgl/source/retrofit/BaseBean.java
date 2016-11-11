package com.bjprd.zcgl.source.retrofit;

import com.google.gson.Gson;

/**
 * Created by 王少岩 on 2016/11/1.
 */
public class BaseBean {
    public BaseStatus status;

    public static class BaseStatus {
        public String code;
        public String msg;

        public Boolean isSuccess() {
            if ("0000".equals(this.code)) {
                return true;
            }
            return false;
        }
    }

    public String toString() {
        return toJson();
    }

    public String toJson() {
        return toJson(this);
    }

    public static String toJson(BaseBean bean) {
        if (bean != null) {
            return new Gson().toJson(bean);
        }
        return null;
    }
}
