package com.gdzc.net;

/**
 * Created by 王少岩 on 2016/9/19.
 */
public class HttpPath {
    /**
     * Url地址
     */
    public static final String SERVER = "http://www.bjprd.com.cn:8080/";//正式网地址
    public static final String SERVER_TEXT = "http://www.bjprd.com.cn:8080/";//测试网地址

    public static final String loginUrl = "AndroidInterface-0.0.1-SNAPSHOT/checkusernamebykey";//登录
    public static final String getFlhUrl = "AndroidInterface-0.0.1-SNAPSHOT/common/getFlh";//获取分类号
    public static final String getTsxxUrl = "AndroidInterface-0.0.1-SNAPSHOT/common/getTsxxByFlhAndDj";//获取分类号
    public static final String getDwUrl = "AndroidInterface-0.0.1-SNAPSHOT/common/getDwlist";//获取单位
    public static final String getMkUrl = "AndroidInterface-0.0.1-SNAPSHOT/common/getMkList";//获取使用方向
    public static final String addnewUrl = "AndroidInterface-0.0.1-SNAPSHOT/zj/addnew";//新增数据
    public static final String imageUploadUrl = "AndroidInterface-0.0.1-SNAPSHOT/zj/imageUpload";//上传图片
    public static final String updateZjUrl = "AndroidInterface-0.0.1-SNAPSHOT/zj/updateZj";//更新数据
    public static final String selectMyZjDataUrl = "AndroidInterface-0.0.1-SNAPSHOT/zj/selectMyZjData";//查询自己录入的数据
    public static final String selectZjByIdUrl = "AndroidInterface-0.0.1-SNAPSHOT/zj/selectZjById";//2.2.7	根据id查询某zj值(用于显示详细信息-卡片显示)
    public static final String selectCchByYqbhUrl = "AndroidInterface-0.0.1-SNAPSHOT/zj/selectCchByYqbh";//2.2.8	查询cch库数据(根据zj资产编号)
}
