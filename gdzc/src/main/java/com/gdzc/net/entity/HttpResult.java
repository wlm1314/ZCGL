package com.gdzc.net.entity;

import com.google.gson.Gson;

/**
 * Created by 王少岩 on 2017/2/5.
 */

public class HttpResult<T> {
    private String whatsystem = "";
    private BaseStatus status;
    private T data;

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

    public String getWhatsystem() {
        return whatsystem;
    }

    public BaseStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String toString() {
        return toJson(this);
    }

    public static String toJson(HttpResult bean) {
        if (bean != null) {
            return new Gson().toJson(bean);
        }
        return null;
    }

    public boolean containsSQRW() {
        return "SQRW".contains(whatsystem);
    }


    public boolean containsDJ() {
        return "DJ".contains(whatsystem);
    }
}
