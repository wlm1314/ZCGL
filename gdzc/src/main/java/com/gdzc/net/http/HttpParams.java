package com.gdzc.net.http;


import com.gdzc.net.consts.MD5Tools;
import com.gdzc.utils.SPUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by 王少岩 on 2016/9/14.
 */
public class HttpParams {

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    public static Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    /**
     * 获取分类号
     *
     * @param flh
     * @param mc
     * @param pageNum
     * @return
     */
    public static Map<String, String> paramGetFlh(String flh, String mc, String pageNum) {
        Map<String, String> map = new HashMap();
        map.put("flh", flh);
        map.put("mc", mc);
        map.put("pageNum", pageNum);
        map.put("pageSize", "20");
        return map;
    }

    /**
     * @param flh
     * @param dj
     * @return
     */
    public static Map<String, String> paramGetTsxx(String flh, String dj) {
        Map<String, String> map = new HashMap();
        map.put("flh", flh);
        map.put("dj", dj);
        return map;
    }

    /**
     * @param dwid
     * @return
     */
    public static Map<String, String> paramGetDwList(String dwid) {
        Map<String, String> map = new HashMap();
        map.put("dwid", dwid);
        return map;
    }

    /**
     * @param bj
     * @return
     */
    public static Map<String, String> paramGetMkList(String bj, String Bj2) {
        Map<String, String> map = new HashMap();
        map.put("bj", bj);
        map.put("Bj2", Bj2);
        return map;
    }

    /**
     * @param whatsystem
     * @param addnewstr
     * @return
     */
    public static Map<String, String> paramAddNew(String whatsystem, String addnewstr) {
        Map<String, String> map = new HashMap();
        map.put("whatsystem", whatsystem);
        map.put("addnewstr", addnewstr);
        return map;
    }

    public static Map<String, String> paramUpdateZj(String zjstr) {
        Map<String, String> map = new HashMap();
        map.put("zjstr", zjstr);
        return map;
    }

    public static Map<String, String> paramSearchMyData(String pageNum, String searchzjstr) {
        Map<String, String> map = new HashMap();
        map.put("pageNum", pageNum);
        map.put("searchzjstr", searchzjstr);
        map.put("pageSize", "20");
        return map;
    }

    public static Map<String, String> paramselectZjById(String id) {
        Map<String, String> map = new HashMap();
        map.put("id", id);
        return map;
    }

    public static Map<String, String> paramselectCchByYqbh(String yqbh) {
        Map<String, String> map = new HashMap();
        map.put("yqbh", yqbh);
        return map;
    }

    public static Map<String, String> paramselectUpdateCchById(String updatecchstr) {
        Map<String, String> map = new HashMap();
        map.put("updatecchstr", updatecchstr);
        return map;
    }

    public static Map<String, String> paramDeleteZjById(String id) {
        Map<String, String> map = new HashMap();
        map.put("id", id);
        return map;
    }

    public static Map<String, String> paramSelectMyDataTotalByCategory(String category, String rksjstart, String rksjend) {
        Map<String, String> map = new HashMap();
        map.put("category", category);
        map.put("rksjstart", rksjstart);
        map.put("rksjend", rksjend);
        return map;
    }

    public static Map<String, String> paramSelectMyAllData(String zcbh, String zcmc, String cfdmc, String cfdbh, String gzrqstart, String gzrqend, String rksjstart, String rksjend, String pageNum) {
        Map<String, String> map = new HashMap();
        map.put("zcbh", zcbh);
        map.put("zcmc", zcmc);
        map.put("cfdmc", cfdmc);
        map.put("cfdbh", cfdbh);
        map.put("gzrqstart", gzrqstart);
        map.put("gzrqend", gzrqend);
        map.put("rksjstart", rksjstart);
        map.put("rksjend", rksjend);
        map.put("pageNum", pageNum);
        map.put("pageSize", "20");
        return map;
    }

    public static Map<String, String> paramSelectMySonData(String zcbh, String zcmc, String cfdmc, String cfdbh, String gzrqstart, String gzrqend, String rksjstart, String rksjend, String pageNum, String son) {
        Map<String, String> map = new HashMap();
        map.put("zcbh", zcbh);
        map.put("zcmc", zcmc);
        map.put("cfdmc", cfdmc);
        map.put("cfdbh", cfdbh);
        map.put("gzrqstart", gzrqstart);
        map.put("gzrqend", gzrqend);
        map.put("rksjstart", rksjstart);
        map.put("rksjend", rksjend);
        map.put("pageNum", pageNum);
        map.put("pageSize", "20");
        map.put("son", son);
        return map;
    }

    public static Map<String, String> paramSelectPoolById(String id) {
        Map<String, String> map = new HashMap();
        map.put("id", id);
        return map;
    }

    /**
     * 存放地
     *
     * @param dwid
     * @return
     */
    public static Map<String, String> paramgetCfdd(String dwid) {
        Map<String, String> map = new HashMap();
        map.put("dwid", dwid);
        map.put("cfdbh", "");
        map.put("cfdmc", "");
        return map;
    }

    /**
     * 人员
     *
     * @param dwid
     * @return
     */
    public static Map<String, String> paramgetRyk(String dwid) {
        Map<String, String> map = new HashMap();
        map.put("dwid", dwid);
        map.put("realName", "");
        map.put("rybh", "");
        return map;
    }

    public static Map<String, RequestBody> paramImageUpload(File imgFile){
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), SPUtils.getString(SPUtils.kUser_username, ""));
        RequestBody md5 = RequestBody.create(MediaType.parse("text/plain"), MD5Tools.getMd5(SPUtils.getString(SPUtils.kUser_username, "")));
        Map<String, RequestBody> map = new HashMap<>();
        map.put("username", username);
        map.put("md5", md5);

        if (imgFile != null) {
            RequestBody fileBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
            map.put("file\"; filename=\""+imgFile.getName()+"", fileBody);
        }
        return map;
    }

    public static Map<String, String> paramList(String pageNum) {
        Map<String, String> map = new HashMap();
        map.put("pageNum", pageNum);
        map.put("pageSize", "20");
        return map;
    }

    public static Map<String, String> paramLyr(String who, String pageNum) {
        Map<String, String> map = new HashMap();
        map.put("who", who);
        map.put("pageNum", pageNum);
        map.put("pageSize", "20");
        return map;
    }

    public static Map<String, String> paramLydw(String dwid, String pageNum) {
        Map<String, String> map = new HashMap();
        map.put("dwid", dwid);
        map.put("pageNum", pageNum);
        map.put("pageSize", "20");
        return map;
    }

}
