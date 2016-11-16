package com.bjprd.zcgl.source.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bjprd.zcgl.App;
import com.bjprd.zcgl.utils.DB_Copy;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 王少岩 on 2016/8/23.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private DatabaseHelper(Context context) {
        super(context, DB_Copy.Companion.getSD_CACHE_DIR() + File.separator + DB_Copy.Companion.getPATH_DB() + File.separator + DB_Copy.Companion.getDATABASE_NAME(), null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DB_Copy.Companion.getSD_CACHE_DIR() + File.separator + DB_Copy.Companion.getPATH_DB() + File.separator + DB_Copy.Companion.getDATABASE_NAME(), null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DB_Copy.Companion.getSD_CACHE_DIR() + File.separator + DB_Copy.Companion.getPATH_DB() + File.separator + DB_Copy.Companion.getDATABASE_NAME(), null,
                SQLiteDatabase.OPEN_READONLY);
    }

    private static DatabaseHelper instance;

    /**
     * 单例获取该Helper
     */
    public static synchronized DatabaseHelper getHelper() {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(App.Companion.getAppContext());
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
