package com.gdzc.syfx.model;

import android.databinding.ObservableField;

import com.gdzc.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2016/12/21.
 */

public class SyfxBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public boolean isFirstPage;
        public boolean isLastPage;
        public List<Syfx> list;
    }

    public static class Syfx implements Serializable {
        public String idX;
        public String bj;
        public String nr;
        public String bj2;
        public String bj3;
        public String bj4;
        public String czdm;
        public String czmc;
        public String 校编号;
        public String 校名称;

        public static String getMC(Syfx syfx) {
            return syfx.校名称;
        }

        public static String getBH(Syfx syfx) {
            return syfx.校编号;
        }

        public final ObservableField<Boolean> checked = new ObservableField<>();
    }

}
