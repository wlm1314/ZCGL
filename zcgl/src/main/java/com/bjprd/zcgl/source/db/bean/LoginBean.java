package com.bjprd.zcgl.source.db.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 王少岩 on 2016/8/23.
 */

@DatabaseTable(tableName = "EMP_CODE")
public class LoginBean {
    @DatabaseField(columnName = "用户名")
    private String userName;
    @DatabaseField(columnName = "密码")
    private String passWord;

    public LoginBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
