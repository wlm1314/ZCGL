package com.bjprd.zcgl.source;

import com.bjprd.zcgl.source.db.LoginDao;

import java.sql.SQLException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 王少岩 on 2016/11/11.
 */

public class DataManager {

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
}
