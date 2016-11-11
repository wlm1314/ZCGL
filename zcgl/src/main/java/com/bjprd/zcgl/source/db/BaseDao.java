package com.bjprd.zcgl.source.db;

import android.app.Activity;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 王少岩 on 2016/8/24.
 */
public abstract class BaseDao<T, Integer> {
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    protected DatabaseHelper mDataBaseHelper;
    protected Activity mActivity;

    protected final String FLAG_SEARCH = "flag_search";
    protected final String FLAG_SEARCH_OR_LIKE = "flag_search_or_like";
    protected final String FLAG_SEARCH_AND_LIKE = "flag_search_and_like";

    protected final String LEFT = "left";
    protected final String RIGHT = "right";

    protected BaseDao() {
        getHelper();
    }

    protected DatabaseHelper getHelper() {
        if (mDataBaseHelper == null) {
            mDataBaseHelper = DatabaseHelper.getHelper();
        }
        return mDataBaseHelper;
    }

    protected abstract Dao<T, Integer> getDao() throws SQLException;

    protected int save(T t) throws SQLException {
        return getDao().create(t);
    }

    protected int update(T t) throws SQLException {
        return getDao().update(t);
    }

    private List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.query(preparedQuery);
    }

    private List<T> query(String attributeName, String attributeValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    protected List<T> queryForAll() throws SQLException {
        return getDao().queryForAll();
    }

    protected List<T> query(int pageNo, HashMap<String, String> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (map != null) {
            Where<T, Integer> where = queryBuilder.where();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                where.eq(entry.getKey(), entry.getValue()).and();
            }
        }
        if (pageNo > 0) {
            queryBuilder.limit(20);
            queryBuilder.offset((pageNo-1)*20);
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    protected List<T> queryOrLike(int pageNo, HashMap<String, String> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (map != null) {
            Where<T, Integer> where = queryBuilder.where();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                where.like(entry.getKey(), "%" + entry.getValue() + "%").or();
            }
        }
        if (pageNo >= 1) {
            queryBuilder.limit(20);
            queryBuilder.offset((pageNo-1)*20);
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    private List<T> queryAndLike(int pageNo, HashMap<String, String> map, String likeFlag) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (map != null) {
            Where<T, Integer> where = queryBuilder.where();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                switch (likeFlag) {
                    case LEFT:
                        where.like(entry.getKey(), "%" + entry.getValue()).and();
                        break;
                    case RIGHT:
                        where.like(entry.getKey(), entry.getValue() + "%").and();
                        break;
                    default:
                        where.like(entry.getKey(), "%" + entry.getValue() + "%").and();
                        break;
                }
            }
        }
        if (pageNo >= 1) {
            queryBuilder.limit(20);
            queryBuilder.offset((pageNo-1)*20);
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }
}
