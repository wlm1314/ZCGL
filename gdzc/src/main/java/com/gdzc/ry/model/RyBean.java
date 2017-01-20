package com.gdzc.ry.model;

import android.databinding.ObservableField;

import com.gdzc.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2017/1/20.
 */

public class RyBean extends BaseBean {

    public DataBean data;

    public static class DataBean {
        public boolean isFirstPage;
        public boolean isLastPage;
        public List<Ry> list;
    }

    public static class Ry implements Serializable {
        public int id;
        public String 单位编号;
        public String 单位名称;
        public String 人员名;
        public String 人员简码;
        public String 人员编号;
        public String 标志;
        public String 反馈信息;
        public String 校单位编号;
        public String 校单位名称;

        public static String getBH(Ry ry){
            return ry.人员编号;
        }

        public static String getMC(Ry ry){
            return ry.人员名;
        }

        public final ObservableField<Boolean> checked = new ObservableField<>();
    }
}
