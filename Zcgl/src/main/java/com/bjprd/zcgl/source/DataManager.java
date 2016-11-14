package com.bjprd.zcgl.source;

import com.bjprd.zcgl.source.db.LoginDao;
import com.bjprd.zcgl.source.db.ZcdjDao;
import com.bjprd.zcgl.source.db.bean.ZcdjBean;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 王少岩 on 2016/11/11.
 */

public class DataManager {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public static Observable<Boolean> login(String username, String password) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    Boolean status = new LoginDao().getData(username, password);
                    subscriber.onNext(status);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 资产登记字段
     * @return
     */
    public static Observable<List<ZcdjBean>> getZcdj(){
        return Observable.create(new Observable.OnSubscribe<List<ZcdjBean>>() {
            @Override
            public void call(Subscriber<? super List<ZcdjBean>> subscriber) {
                try {
                    List<ZcdjBean> list = new ZcdjDao().getData();
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
