package com.bjprd.zcgl.source.db.bean;

import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 王少岩 on 2016/11/14.
 */

@DatabaseTable(tableName = "z_tsxx_qc")
public class TsxxBean {
    @DatabaseField(columnName = "序号")
    public int num;
    @DatabaseField(columnName = "字段名")
    public String columName;
    @DatabaseField(columnName = "英文字段")
    public String columEng;
    @DatabaseField(columnName = "修改否")
    public String isEdit;
    @DatabaseField(columnName = "必填否")
    public String isNull;
    @DatabaseField(columnName = "提示内容")
    public String tsnr;
    @DatabaseField(columnName = "显示内容")
    public String xsnr;
    @DatabaseField(columnName = "模块显示")
    public String mkxs;
    @DatabaseField(columnName = "id")
    public int id;
    @DatabaseField(columnName = "登记否")
    public String isDj;
    @DatabaseField(columnName = "代码取值否")
    public String isQz;
    @DatabaseField(columnName = "默认值")
    public String defValue;
    @DatabaseField(columnName = "字段类型")
    public String columType;
    @DatabaseField(columnName = "字段长度")
    public int columLen;
    @DatabaseField(columnName = "查询显示")
    public String cxxs;
    @DatabaseField(columnName = "登记必填")
    public String djbt;
    @DatabaseField(columnName = "卡片显示")
    public String kpxs;

    public static boolean isNull(String str) {
        return str.equals("1");
    }

    public ObservableField<String> editText = new ObservableField<>();

    public ReplyCommand clickCommand = new ReplyCommand(() -> {
        switch (columEng) {
            case "lydwh":
                break;
            case "syfx":
                break;
        }
    });
}
