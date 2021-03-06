package com.gdzc.zcdj.zcdj.viewmodel;

import android.databinding.ObservableField;

import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.zcdj.zcdj.view.ZcxgFragment;
import com.kelin.mvvmlight.command.ReplyCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcxgViewModel {
    private ZcxgFragment mFragment;

    public ZcxgViewModel(ZcxgFragment fragment) {
        mFragment = fragment;
    }

    public final ObservableField<String> zcmc = new ObservableField<>();
    public final ObservableField<String> xh = new ObservableField<>();
    public final ObservableField<String> gg = new ObservableField<>();
    public final ObservableField<String> cj = new ObservableField<>();
    public final ObservableField<String> cch = new ObservableField<>();

    public String searchJson = "";

    public ReplyCommand searchCommand = new ReplyCommand(() -> {
        JSONObject json = new JSONObject();
        try {
            json.put("资产名称", zcmc.get());
            json.put("型号", xh.get());
            json.put("规格", gg.get());
            json.put("厂家", cj.get());
            json.put("出厂号", cch.get());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        searchJson = json.toString();
        mFragment.setPageNo(1);
        getData(1);
    });

    public void getData(int pageNum) {
        HttpRequest.SearchMyData(HttpParams.paramSearchMyData(pageNum + "", searchJson))
                .subscribe(new RetrofitSubscriber<>(zcbgBean -> mFragment.setData(zcbgBean.data)));
    }
}
