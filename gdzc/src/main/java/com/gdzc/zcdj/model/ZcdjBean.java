package com.gdzc.zcdj.model;

import com.gdzc.base.BaseBean;

import java.util.List;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public List<Zcdj> list;
    }

    public static class Zcdj {
        public String id;
        public String 序号;
        public String 字段名;
        public String 英文字段;
        public String 修改否;
        public String 必填否;
        public String 提示内容;
        public String 显示内容;
        public String 模块显示;
        public String 登记否;
        public String 代码取值否;
        public String 默认值;
        public String 字段类型;
        public String 字段长度;
        public String 查询显示;
        public String 登记必填;
        public String 卡片显示;
    }
}
