package com.gdzc.zcdj.model;

import android.databinding.ObservableField;

import com.gdzc.base.BaseBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 王少岩 on 2017/1/9.
 */

public class ZcxgEditBean extends BaseBean {
    public List<DataBean> data;

    /**
     * "字段名":"资产名称",
     * "修改否":"0",
     * "必填否":"1",
     * "显示内容":"资产名称",
     * "代码取值否":"0",
     * "值":"哈哈"
     */
    public static class DataBean {
        public String 字段名;
        public String 修改否;
        public String 必填否;
        public String 显示内容;
        public String 代码取值否;
        public String 值;

        public String get值() {
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.format(sdf1.parse(值));
            } catch (ParseException e) {
                return 值.equals("null") ? "" : 值;
            }
        }
    }

    public static class Zcxg {
        public String colName;
        public String isEdit;
        public String isNull;
        public String xsnr;
        public String isQz;
        public ObservableField<String> val = new ObservableField<>();

        public static Zcxg castToZcxb(DataBean data) {
            Zcxg zcbg = new Zcxg();
            zcbg.colName = data.字段名;
            zcbg.isEdit = data.修改否;
            zcbg.isNull = data.必填否;
            zcbg.xsnr = data.显示内容;
            zcbg.isQz = data.字段名.equals("购置日期") ? "1" : data.代码取值否;
            zcbg.val.set(data.get值());
            return zcbg;
        }
    }
}
