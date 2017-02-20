package com.gdzc.zcdj.mk.model;

import android.databinding.ObservableField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class MKBean {
    public boolean isFirstPage;
    public boolean isLastPage;
    public List<Mk> list;

    public static class Mk implements Serializable {
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

        public String getMC() {
            return nr.substring(2, nr.length());
        }

        public String getBH() {
            return nr.substring(0, 1);
        }

        public final ObservableField<Boolean> checked = new ObservableField<>();
    }
}
