package com.gdzc.zctj.model;

import com.gdzc.base.BaseBean;

import java.util.List;

/**
 * Created by 王少岩 on 2017/1/17.
 */

public class ZctjBean extends BaseBean {

    public DataBean data;

    public static class DataBean {
        public boolean isFirstPage;
        public boolean isLastPage;
        public List<Zctj> list;
    }

    public static class Zctj {
        public String name;
        public int totalCount;
        public int totalMoney;
    }
}
