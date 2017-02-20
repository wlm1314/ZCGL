package com.gdzc.zcdj.mk.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2017/1/20.
 */

public class CfdBean {
    public boolean isFirstPage;
    public boolean isLastPage;
    public List<Cfd> list;

    public static class Cfd implements Serializable {
        public int id;
        public String 单位编号;
        public String 单位名称;
        public String 存放地简码;
        public String 存放地号;
        public String 存放地名;
        public String 标志;
        public String 反馈信息;
        public String 校单位编号;
        public String 校单位名称;

        public static String getBH(Cfd cfd) {
            return cfd.单位编号;
        }

        public static String getMC(Cfd cfd) {
            return cfd.单位名称;
        }

    }
}
