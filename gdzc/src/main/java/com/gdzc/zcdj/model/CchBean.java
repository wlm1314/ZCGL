package com.gdzc.zcdj.model;

import com.gdzc.base.BaseBean;

import java.util.List;

/**
 * Created by 王少岩 on 2017/1/12.
 */

public class CchBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public boolean isFirstPage;
        public boolean isLastPage;
        public List<ListBean> list;
    }

    public static class ListBean {
        public String id;
        public String 资产编号;
        public String 出厂号;
        public String 输入人;
        public String 领用单位号;
        public String 领用人;
        public String 存放地编号;
        public String 存放地名称;
        public String 标识;
        public String 单据号;
        public String 人员编号;
    }

    public static class Cch {
        public String id;
        public String zcbh;
        public String cch;
        public String srr;
        public String lydwh;
        public String lyr;
        public String cfdbh;
        public String cfdmc;
        public String bs;
        public String djh;
        public String rybh;

        public static Cch castToCch(ListBean bean) {
            Cch cch = new Cch();
            cch.id = bean.id;
            cch.zcbh = bean.资产编号;
            cch.cch = bean.出厂号;
            cch.srr = bean.输入人;
            cch.lydwh = bean.领用单位号;
            cch.lyr = bean.领用人;
            cch.cfdbh = bean.存放地编号;
            cch.cfdmc = bean.存放地名称;
            cch.bs = bean.标识;
            cch.djh = bean.单据号;
            cch.rybh = bean.人员编号;
            return cch;
        }
    }
}