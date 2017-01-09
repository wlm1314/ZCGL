package com.gdzc.zcbg.viewmodel;

import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.zcbg.view.ZcbgActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcbgViewModel {
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
        ((ZcbgActivity) App.getAppContext().getCurrentActivity()).setPageNo(1);
        getData(1);
    });

    public void getData(int pageNum) {
        HttpRequest.SearchMyData(HttpPostParams.paramSearchMyData(pageNum + "", searchJson))
                .subscribe(new RetrofitSubscriber<>(zcbgBean -> {
                    ((ZcbgActivity) App.getAppContext().getCurrentActivity()).setData(zcbgBean.data);
                }));
    }
}
