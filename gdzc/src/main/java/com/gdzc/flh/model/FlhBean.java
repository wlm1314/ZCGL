package com.gdzc.flh.model;

import android.databinding.ObservableField;

import com.gdzc.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class FlhBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public boolean isLastPage;
        public List<Flh> list;
    }

    public static class Flh implements Serializable{
        public String id;
        public String flh;
        public String mc;
        public String czh;
        public String czm;
        public String bzf;
        public String cybz;
        public String zdyh;
        public String zdym;
        public String 名称简码;
        public String 计量单位;
        public String 标志;
        public String 标准分类;
        public String czrwid;
        public String czrwid1;
        public String 六大分类;
        public String 使用年限;
        public String 残值百分比;

        public final ObservableField<Boolean> checked = new ObservableField<>();
    }
}
