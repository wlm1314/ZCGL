package com.gdzc.net;

import com.gdzc.base.BaseBean;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.login.model.LoginBean;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.zcdj.model.CchBean;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.model.ZcxgEditBean;
import com.gdzc.zcdj.model.ZcdjBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
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
    Observable<LoginBean> Login(@FieldMap Map<String, String> params);

    //获取分类号
    @FormUrlEncoded
    @POST(HttpPath.getFlhUrl)
    Observable<FlhBean> GetFlh(@FieldMap Map<String, String> params);

    //资产登记页面
    @FormUrlEncoded
    @POST(HttpPath.getTsxxUrl)
    Observable<ZcdjBean> GetTsxx(@FieldMap Map<String, String> params);

    //获取单位列表
    @FormUrlEncoded
    @POST(HttpPath.getDwUrl)
    Observable<LydwBean> GetDwList(@FieldMap Map<String, String> params);

    //获取使用方向
    @FormUrlEncoded
    @POST(HttpPath.getMkUrl)
    Observable<SyfxBean> GetMkList(@FieldMap Map<String, String> params);

    //新增数据
    @FormUrlEncoded
    @POST(HttpPath.addnewUrl)
    Observable<BaseBean> AddNew(@FieldMap Map<String, String> params);

    //上传图片
    @POST(HttpPath.imageUploadUrl)
    @Multipart
    Observable<BaseBean> ImageUpload(@Part("file\"; filename=\"test.jpg\"") RequestBody file, @QueryMap Map<String, String> params);

    //更新数据
    @FormUrlEncoded
    @POST(HttpPath.updateZjUrl)
    Observable<BaseBean> UpdateZj(@FieldMap Map<String, String> params);

    //新增数据
    @FormUrlEncoded
    @POST(HttpPath.selectMyZjDataUrl)
    Observable<ZcxgBean> SearchMyData(@FieldMap Map<String, String> params);

    //根据Id查询zj
    @FormUrlEncoded
    @POST(HttpPath.selectZjByIdUrl)
    Observable<ZcxgEditBean> SearchZJById(@FieldMap Map<String, String> params);

    //查询cch
    @FormUrlEncoded
    @POST(HttpPath.selectCchByYqbhUrl)
    Observable<CchBean> SelectCchByYqbh(@FieldMap Map<String, String> params);

    //修改cch
    @FormUrlEncoded
    @POST(HttpPath.updateCchByIdUrl)
    Observable<BaseBean> UpdateCchById(@FieldMap Map<String, String> params);

}
