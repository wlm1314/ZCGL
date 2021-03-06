package com.gdzc.net.http;

import com.gdzc.base.BaseBean;
import com.gdzc.login.model.LoginBean;
import com.gdzc.net.consts.HttpPath;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.zccx.model.ZccxBean;
import com.gdzc.zcdj.flh.model.FlhBean;
import com.gdzc.zcdj.mk.model.CfdBean;
import com.gdzc.zcdj.mk.model.DwBean;
import com.gdzc.zcdj.mk.model.MKBean;
import com.gdzc.zcdj.mk.model.RyBean;
import com.gdzc.zcdj.cch.model.CchBean;
import com.gdzc.zcdj.zcdj.model.TsxxBean;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.zcdj.model.ZcxgEditBean;
import com.gdzc.zctj.model.ZctjBean;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by 王少岩 on 2016/11/9.
 */

/**
 * retrofit 以注解的方式访问接口
 * <p>
 * post请求方式如下
 *
 * @FormUrlEncoded
 * @POST(url) Observable<T> method(@FieldMap Map<String, String> params);
 */
public interface RequestApi {
    //登录
    @FormUrlEncoded
    @POST(HttpPath.loginUrl)
    Observable<HttpResult<LoginBean>> Login(@FieldMap Map<String, String> params);

    //获取分类号
    @FormUrlEncoded
    @POST(HttpPath.getFlhUrl)
    Observable<HttpResult<FlhBean>> GetFlh(@FieldMap Map<String, String> params);

    //资产登记页面
    @FormUrlEncoded
    @POST(HttpPath.getTsxxUrl)
    Observable<HttpResult<TsxxBean>> GetTsxx(@FieldMap Map<String, String> params);

    //获取单位列表
    @FormUrlEncoded
    @POST(HttpPath.getDwUrl)
    Observable<HttpResult<DwBean>> GetDwList(@FieldMap Map<String, String> params);

    //获取使用方向
    @FormUrlEncoded
    @POST(HttpPath.getMkUrl)
    Observable<HttpResult<MKBean>> GetMkList(@FieldMap Map<String, String> params);

    //获取人员
    @FormUrlEncoded
    @POST(HttpPath.getRykUrl)
    Observable<HttpResult<RyBean>> GetRyList(@FieldMap Map<String, String> params);

    //获取存放地
    @FormUrlEncoded
    @POST(HttpPath.getCfddUrl)
    Observable<HttpResult<CfdBean>> GetCfdList(@FieldMap Map<String, String> params);

    //新增数据
    @FormUrlEncoded
    @POST(HttpPath.addnewUrl)
    Observable<HttpResult> AddNew(@FieldMap Map<String, String> params);

    //上传图片
    @POST(HttpPath.imageUploadUrl)
    @Multipart
    Observable<HttpResult<String>> ImageUpload(@PartMap Map<String, RequestBody> params);

    //更新数据
    @FormUrlEncoded
    @POST(HttpPath.updateZjUrl)
    Observable<HttpResult> UpdateZj(@FieldMap Map<String, String> params);

    //新增数据
    @FormUrlEncoded
    @POST(HttpPath.selectMyZjDataUrl)
    Observable<ZcxgBean> SearchMyData(@FieldMap Map<String, String> params);

    //根据Id查询zj
    @FormUrlEncoded
    @POST(HttpPath.selectZjByIdUrl)
    Observable<HttpResult<ArrayList<ZcxgEditBean>>> SearchZJById(@FieldMap Map<String, String> params);

    //查询cch
    @FormUrlEncoded
    @POST(HttpPath.selectCchByYqbhUrl)
    Observable<HttpResult<CchBean>> SelectCchByYqbh(@FieldMap Map<String, String> params);

    //修改cch
    @FormUrlEncoded
    @POST(HttpPath.updateCchByIdUrl)
    Observable<BaseBean> UpdateCchById(@FieldMap Map<String, String> params);

    //删除zj
    @FormUrlEncoded
    @POST(HttpPath.deleteZjByIdUrl)
    Observable<BaseBean> DeleteZjById(@FieldMap Map<String, String> params);

    //统计
    @FormUrlEncoded
    @POST(HttpPath.selectMyDataTotalByCategoryUrl)
    Observable<ZctjBean> SelectMyDataTotalByCategory(@FieldMap Map<String, String> params);

    //查询已审核的数据
    @FormUrlEncoded
    @POST(HttpPath.selectMyAllDataUrl)
    Observable<ZcxgBean> SelectMyAllData(@FieldMap Map<String, String> params);

    //查询已审核的数据
    @FormUrlEncoded
    @POST(HttpPath.selectPoolByIdUrl)
    Observable<ZccxBean> SelectPoolById(@FieldMap Map<String, String> params);

    //查询已审核的数据
    @FormUrlEncoded
    @POST(HttpPath.selectMySonData3Url)
    Observable<ZcxgBean> selectMySonData(@FieldMap Map<String, String> params);

    //个人资产查询
    @FormUrlEncoded
    @POST(HttpPath.underMyNameDataUrl)
    Observable<ZcxgBean> underMyNameData(@FieldMap Map<String, String> params);

    //领用人查询
    @FormUrlEncoded
    @POST(HttpPath.recipientsWhoUrl)
    Observable<ZcxgBean> recipientsWho(@FieldMap Map<String, String> params);

    //领用单位查询
    @FormUrlEncoded
    @POST(HttpPath.selectDataByDeptUrl)
    Observable<ZcxgBean> selectDataByDept(@FieldMap Map<String, String> params);

}
