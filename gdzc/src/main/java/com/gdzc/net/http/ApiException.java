package com.gdzc.net.http;


import com.gdzc.utils.Utils;

/**
 * Created by 王少岩 on 2017/2/5.
 */

public class ApiException extends RuntimeException {

    public ApiException(String code, String msg) {
        super(msg);
        Utils.showToast(msg);
    }

}