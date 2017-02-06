package com.gdzc.net.consts;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王少岩 on 2016/12/19.
 */

public class MD5Tools {
    public static String getMd5(String str) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update((str.trim() + getStringDate().trim()).getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getStringDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
    }
}
