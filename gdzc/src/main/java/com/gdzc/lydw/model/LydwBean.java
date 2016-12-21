package com.gdzc.lydw.model;

import android.databinding.ObservableField;

import com.gdzc.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2016/12/21.
 */

public class LydwBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public boolean isFirstPage;
        public boolean isLastPage;
        public List<Lydw> list;
    }

    public static class Lydw implements Serializable {
        public String dwId;
        public String dwName;
        public String pid;
        public String id;
        public String sbNum;
        public String sbPrice;
        public String jjNum;
        public String jjPrice;
        public String dzpNum;
        public String dzpPrice;
        public Object houseCount;
        public String houseUarea;
        public String houseBarea;
        public String wwPrice;
        public String wwNum;
        public String bfPrice;
        public String bfNum;
        public String tsPrice;
        public String tsNum;
        public String dzwPrice;
        public String dzwNum;
        public String carNum;
        public String carPrice;
        public String clpNum;
        public String clpPrice;

        public final ObservableField<Boolean> checked = new ObservableField<>();
    }

}
