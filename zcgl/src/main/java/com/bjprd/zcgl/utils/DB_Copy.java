package com.bjprd.zcgl.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bjprd.zcgl.App;
import com.bjprd.zcgl.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 王少岩 on 2016/8/23.
 */
public class DB_Copy {
    private Context mContext;
    public static final String DATABASE_NAME = "gdzc.db";
    public static final File SD_CACHE_DIR = App.getAppContext().getExternalCacheDir();
    public static final String PATH_DB = "databases";

    public DB_Copy(Context context) {
        mContext = context;
    }

    /**
     * 存储数据库文件
     */
    public void copyDataBase() {
        File dir = new File(SD_CACHE_DIR, PATH_DB);
        if (!dir.exists()) dir.mkdir();
        File dest = new File(dir, DATABASE_NAME);
        if(dest.exists()) return;
        try {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dest, null);
            InputStream is = mContext.getResources().openRawResource(R.raw.gdzc);
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] buffer = new byte[8192];
            int count = 0;
            // 开始复制gdzc.db文件
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
            db.close();
        } catch (IOException e) {
            Log.e("COPY_DB","数据库复制出错");
            e.printStackTrace();
        }
    }
}
