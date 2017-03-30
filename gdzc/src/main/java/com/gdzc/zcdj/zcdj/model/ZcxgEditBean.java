package com.gdzc.zcdj.zcdj.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 王少岩 on 2017/1/9.
 */

public class ZcxgEditBean {
    /**
     * "字段名":"资产名称",
     * "修改否":"0",
     * "必填否":"1",
     * "显示内容":"资产名称",
     * "代码取值否":"0",
     * "值":"哈哈"
     */
    public String 字段名;
    public String 修改否;
    public String 必填否;
    public String 显示内容;
    public String 代码取值否;
    public String 字段类型;
    public String 值;
    public String 汉字取值;
    public String 提示内容;

    public String get值() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(sdf1.parse(值));
        } catch (ParseException e) {
            return 值.equals("null") ? "" : 值;
        }
    }

    public String get汉字取值() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (汉字取值 == null)
                汉字取值 = "null";
            return sdf.format(sdf1.parse(汉字取值));
        } catch (ParseException e) {
            return 汉字取值.equals("null") ? "" : 汉字取值;
        }
    }
}
