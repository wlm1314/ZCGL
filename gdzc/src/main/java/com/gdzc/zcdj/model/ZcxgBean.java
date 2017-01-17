package com.gdzc.zcdj.model;

import com.gdzc.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcxgBean extends BaseBean {

    public TotalBean total;
    public DataBean data;

    public static class TotalBean {
        public int totalCount;
        public int totalMoney;
        public int totalRow;
    }

    public static class DataBean {
        public boolean isFirstPage;
        public boolean isLastPage;
        public List<Zcxg> list;
    }

    public static class Zcxg implements Serializable {
        public String id;
        public String 领用单位号;
        public String 资产编号;
        public String 资产名称;
        public String 型号;
        public String 规格;
        public String 数量;
        public String 单价;
        public String 金额;
        public String 厂家;
        public String 购置日期;
        public String 入库时间;
        public String 现状;
        public String 经费科目;
        public String 使用方向;
        public String 资产来源;
        public String 领用人;
        public String 经手人;
        public String 单据号;
        public String 存放地名称;
        public String 输入人;
        public String 记帐人;
        public String 出厂日期;
        public String 财务凭单;
        public String 出厂号;
        public String 输入日期;
        public String 变动日期;
        public String 备注;
        public String 资产号;
        public String 科研号;
        public String 折旧方式;
        public String 字符字段1;
        public String 字符字段2;
        public String 字符字段3;
        public String 字符字段4;
        public String 字符字段5;
        public String 数字字段1;
        public String 数字字段2;
        public String 数字字段3;
        public String 数字字段4;
        public String 资产类别;
        public String 存放地编号;
        public String 国标分类号;
        public String 国标分类名;
        public String 分类号;
        public String 使用单位号;
        public String 保修期限;
        public String 国别;
        public String 国别码;
        public String 批量;
        public String 校区;
        public String 图片文件;
        public String 图文名称;
        public String 图片文件1;
        public String 图文名称1;
        public String 图片文件2;
        public String 图文名称2;
        public String 图文名称3;
        public String 图片文件3;
        public String 审核;
        public String 清查方式;
        public String 清查日期;
        public String 清查异常;
        public String 清查工号;
        public String 清查人;
        public String 清查备注;
        public String 标志;
        public String 财务审核;
        public String 财审核日期;
        public String 财务审核人;
        public String 初审;
        public String 初审人;
        public String 初审日期;
        public String 人员编号;
        public String 财政分类id;
        public String 财政大类id;
        public String 六类资产;
        public String 供货商;
        public String 价值类型;
        public String 发票号;
        public String 字符字段6;
        public String 字符字段7;
        public String 字符字段8;
        public String 字符字段9;
        public String 字符字段10;
        public String 日期字段1;
        public String 日期字段2;
        public String 代码字段1;
        public String 代码字段2;
        public String 代码字段3;
        public String 使用年限;
        public String 累计折旧额;
        public String 折旧残值;
        public String 净值;
        public String 计量单位;
        public String 变动领用人;
        public String 变人员编号;
        public String 变动地名称;
        public String 变动地编号;
        public String 变动单据号;
        public String 采购形式;
        public String 使用面积;
        public String 建筑面积;
        public String 房屋产别;
        public String 占地面积;
        public String 公摊面积;
        public String 租金;
        public String 子系统;
        public String 月折旧额;
        public String 折旧年月;
        public String 折旧日期;
        public String 房屋结构;
        public String 房屋用途;
        public String 证办理情况;
        public String 产权证字号;
        public String 竣工日期;
        public String 设计单位;
        public String 施工单位;
        public String 校园规划用途;
        public String 房间数量;
        public String 分割面积;
        public String 人防级别;
        public String 发证日期;
        public String 地上层数;
        public String 地下层数;
        public String 发证机关;
        public String 车辆用途;
        public String 使用性质;
        public String 车牌号;
        public String 排气量;
        public String 发动机号;
        public String 编制情况;
        public String 核定载客;
        public String 品牌性质;
        public String 纲属科;
        public String 拉丁文名;
        public String 档案号;
        public String 版本类别;
        public String 最大授权数;
        public String 授权期限;
        public String 文物等级;
        public String 入藏年代;
        public String 公布级别;
        public String 公布年代;
        public String 材质类型;
        public String 残缺情况;
    }

}
