package com.bjprd.zcgl.source.db;

import com.bjprd.zcgl.source.db.bean.TsxxBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 王少岩 on 2016/8/23.
 */
public class ZcdjDao extends BaseDao<TsxxBean, Integer> {

    public static final String LOGIN_TAG = "login";

    public ZcdjDao() {
        super();
    }

    @Override
    public Dao<TsxxBean, Integer> getDao() throws SQLException {
        return getHelper().getDao(TsxxBean.class);
    }

    public List<TsxxBean> getData() throws SQLException {
        List<TsxxBean> list = queryForAll();
        return list;
    }
}
