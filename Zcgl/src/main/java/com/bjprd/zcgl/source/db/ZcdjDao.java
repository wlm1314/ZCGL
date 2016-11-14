package com.bjprd.zcgl.source.db;

import com.bjprd.zcgl.source.db.bean.ZcdjBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 王少岩 on 2016/8/23.
 */
public class ZcdjDao extends BaseDao<ZcdjBean, Integer> {

    public static final String LOGIN_TAG = "login";

    public ZcdjDao() {
        super();
    }

    @Override
    public Dao<ZcdjBean, Integer> getDao() throws SQLException {
        return getHelper().getDao(ZcdjBean.class);
    }

    public List<ZcdjBean> getData() throws SQLException {
        List<ZcdjBean> list = queryForAll();
        return list;
    }
}
