package com.bjprd.zcgl.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.text.DecimalFormat;

/**
 * 作者: Administrator on 2015/9/22 15:43.
 * 邮箱: likaileeopen@163.com
 */
public class RMB {
    /**
     * 设置金额
     *
     * @param integer_length
     *            整数位数
     * @param decimal_length
     *            小数位数
     */
    public static InputFilter createInputFilter(final int integer_length,
                                                final int decimal_length) {
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                if (dValue.contains(".")) {
                    int index = dValue.indexOf(".");
                    int before_length = dValue
                            .substring(0, dValue.indexOf(".")).length();
                    int end_length = dValue.substring(dValue.indexOf(".") + 1)
                            .length();
                    if (source.length() > 1 && !"".equals(dValue)) {// 屏蔽粘贴数据
                        return "";
                    }
                    if (dstart < index + 1) {
                        if (integer_length > before_length) {
                            return String.valueOf(source);
                        } else {
                            return "";
                        }

                    } else if (dstart > index - 1) {
                        if (decimal_length > end_length) {
                            String[] splitArray = dValue.split("\\.");
                            if (splitArray.length > 1) {
                                String dotValue = splitArray[1];
                                int diff = dotValue.length() + 1
                                        - decimal_length;
                                if (diff > 0) {
                                    String ret = String.valueOf(source
                                            .subSequence(start, end - diff));
                                    return ret;
                                }
                            }
                        } else {
                            return "";
                        }
                    }

                } else {
                    if (source.length() > 1 && !"".equals(dValue)) {// 屏蔽粘贴数据
                        return "";
                    } else {
                        if (dValue.length() >= integer_length
                                && 0 != decimal_length) {
                            if (".".equals(source)) {
                                String ret = String.valueOf(".");
                                return ret;
                            } else {
                                String ret = String.valueOf("");
                                return ret;
                            }
                        } else if (dValue.length() >= integer_length
                                && 0 == decimal_length) {
                            String ret = String.valueOf("");
                            return ret;
                        }
                    }
                }
                return null;
            }

        };
        return lengthfilter;
    }

    public static String formateDec(double d){
        DecimalFormat df   = new DecimalFormat("######0.00");
        return df.format(d);
    }
}
