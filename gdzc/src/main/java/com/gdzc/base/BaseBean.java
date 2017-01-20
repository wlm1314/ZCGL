package com.gdzc.base;

import com.google.gson.Gson;

/**
 * Created by 王少岩 on 2016/11/1.
 */

/**
 * 接口返回json数据对应javaBean的基类
 * 具体类型继承BaseBean并在子类添加data属性
 */
public class BaseBean {
    public String whatsystem="";
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

    public boolean containsSQRW(){
        return "SQRW".contains(whatsystem);
    }


    public boolean containsDJ(){
        return "DJ".contains(whatsystem);
    }
}
