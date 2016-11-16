package com.bjprd.zcgl.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bjprd.zcgl.App
import com.bjprd.zcgl.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by 王少岩 on 2016/8/23.
 */
class DB_Copy(private val mContext: Context) {

    /**
     * 存储数据库文件
     */
    fun copyDataBase() {
        val dir = File(SD_CACHE_DIR, PATH_DB)
        if (!dir.exists()) dir.mkdir()
        val dest = File(dir, DATABASE_NAME)
        if (dest.exists()) return
        try {
            val db = SQLiteDatabase.openOrCreateDatabase(dest, null)
            val ins = mContext.resources.openRawResource(R.raw.zcgl)
            val fos = FileOutputStream(dest)
            val buffer = ByteArray(8192)
            var count = ins.read(buffer)
            // 开始复制gdzc.db文件
            while (count>0) {
                fos.write(buffer, 0, count)
                count = ins.read(buffer)
            }
            fos.close()
            ins.close()
            db.close()
        } catch (e: IOException) {
            Log.e("COPY_DB", "数据库复制出错")
            e.printStackTrace()
        }

    }

    companion object {
        val DATABASE_NAME = "zcgl.db"
        val SD_CACHE_DIR = App.appContext!!.externalCacheDir
        val PATH_DB = "databases"
    }
}
