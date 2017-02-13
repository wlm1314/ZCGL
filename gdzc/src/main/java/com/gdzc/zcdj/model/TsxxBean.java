package com.gdzc.zcdj.model;

import java.util.List;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class TsxxBean {
    public List<Server> list;

    public static class Server {
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

    public static class Tsxx {
        public String id;
        public String num;
        public String columName;
        public String columEng;
        public String isEdit;
        public String isNull;
        public String tsnr;
        public String xsnr;
        public String mkxs;
        public String isDj;
        public String isQz;
        public String defValue;
        public String columType;
        public String columLen;
        public String cxxs;
        public String djbt;
        public String kpxs;

        public static Tsxx castToTsxx(Server bean) {
            Tsxx tsxx = new Tsxx();
            tsxx.id = bean.id;
            tsxx.num = bean.序号;
            tsxx.columName = bean.字段名;
            tsxx.columEng = bean.英文字段;
            tsxx.isEdit = bean.修改否;
            tsxx.isNull = bean.必填否;
            tsxx.tsnr = bean.提示内容;
            tsxx.xsnr = bean.显示内容;
            tsxx.mkxs = bean.模块显示;
            tsxx.isDj = bean.登记否;
            tsxx.isQz = bean.代码取值否;
            tsxx.defValue = bean.默认值;
            tsxx.columType = bean.字段类型;
            tsxx.columLen = bean.字段长度;
            tsxx.cxxs = bean.查询显示;
            tsxx.djbt = bean.登记必填;
            tsxx.kpxs = bean.卡片显示;
            return tsxx;
        }
    }
}
