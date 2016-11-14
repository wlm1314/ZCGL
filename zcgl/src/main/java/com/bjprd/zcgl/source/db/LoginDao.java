package com.bjprd.zcgl.source.db;

import com.bjprd.zcgl.source.db.bean.LoginBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 王少岩 on 2016/8/23.
 */
public class LoginDao extends BaseDao<LoginBean, Integer> {

    public static final String LOGIN_TAG = "login";

    public LoginDao() {
        super();
    }

    @Override
    public Dao<LoginBean, Integer> getDao() throws SQLException {
        return getHelper().getDao(LoginBean.class);
    }

    public boolean getData(String username, String password) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        List<LoginBean> list = query(-1, map);
        return list.size() > 0 ? true : false;
    }
}
