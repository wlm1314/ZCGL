package com.gdzc.zccx.viewmodel;

import android.app.Dialog;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.binding.command.ReplyCommand;
import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.utils.Utils;
import com.gdzc.zccx.view.ZccxActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王少岩 on 2017/1/19.
 */

public class ZccxViewModel {
    private String son;

    public ZccxViewModel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        zcbh.set("");
        zcmc.set("");
        cfdmc.set("");
        cfdbh.set("");
        gz_start.set("1900-01-01");
        gz_end.set(now);
        rk_start.set("1900-01-01");
        rk_end.set(now);
        child.set("全部");
    }

    public final ObservableField<String> zcbh = new ObservableField<>();
    public final ObservableField<String> zcmc = new ObservableField<>();
    public final ObservableField<String> cfdmc = new ObservableField<>();
    public final ObservableField<String> cfdbh = new ObservableField<>();
    public final ObservableField<String> gz_start = new ObservableField<>();
    public final ObservableField<String> gz_end = new ObservableField<>();
    public final ObservableField<String> rk_start = new ObservableField<>();
    public final ObservableField<String> rk_end = new ObservableField<>();
    public final ObservableField<String> child = new ObservableField<>();

    public ReplyCommand gzStartCommand = new ReplyCommand(() -> showTimePicker("购置开始时间", gz_start));
    public ReplyCommand gzEndCommand = new ReplyCommand(() -> showTimePicker("购置结束时间", gz_end));
    public ReplyCommand rkStartCommand = new ReplyCommand(() -> showTimePicker("入库开始时间", rk_start));
    public ReplyCommand rkEndCommand = new ReplyCommand(() -> showTimePicker("入库结束时间", rk_end));
    public ReplyCommand childCommand = new ReplyCommand(() -> showTypeView());
    public ReplyCommand searchCommand = new ReplyCommand(() -> {
        if (TextUtils.isEmpty(son))
            getData(1);
        else
            getChildData(1);
    });

    public void getData(int pageNo) {
        HttpRequest.SelectMyAllData(HttpParams.paramSelectMyAllData(zcbh.get(), zcmc.get(), cfdmc.get(), cfdbh.get(), gz_start.get(), gz_end.get(), rk_start.get(), rk_end.get(), pageNo + ""))
                .subscribe(new RetrofitSubscriber<>(zcxgBean -> {
                    ((ZccxActivity) App.getAppContext().getCurrentActivity()).complete();
                    if (pageNo == 1)
                        ((ZccxActivity) App.getAppContext().getCurrentActivity()).setPageNo(1);
                    ((ZccxActivity) App.getAppContext().getCurrentActivity()).setData(zcxgBean);
                }, throwable -> ((ZccxActivity) App.getAppContext().getCurrentActivity()).complete()));
    }

    public void getChildData(int pageNo) {
        HttpRequest.selectMySonData(HttpParams.paramSelectMySonData(zcbh.get(), zcmc.get(), cfdmc.get(), cfdbh.get(), gz_start.get(), gz_end.get(), rk_start.get(), rk_end.get(), pageNo + "", son))
                .subscribe(new RetrofitSubscriber<>(zcxgBean -> {
                    ((ZccxActivity) App.getAppContext().getCurrentActivity()).complete();
                    if (pageNo == 1)
                        ((ZccxActivity) App.getAppContext().getCurrentActivity()).setPageNo(1);
                    ((ZccxActivity) App.getAppContext().getCurrentActivity()).setData(zcxgBean);
                }, throwable -> ((ZccxActivity) App.getAppContext().getCurrentActivity()).complete()));
    }

    public void showTimePicker(String title, ObservableField<String> editText) {
        TimePickerView mTimePickerView = new TimePickerView(App.getAppContext().getCurrentActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setTitle(title);
        mTimePickerView.setTime(new Date());
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(date -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            editText.set(sdf.format(date));
        });
    }

    public void showTypeView() {
        View view = App.getAppContext().getCurrentActivity().getLayoutInflater().inflate(R.layout.layout_child, null);
        Dialog dialog = Utils.showBottomDialog(App.getAppContext().getCurrentActivity(), view);
        view.findViewById(R.id.tv_sbsj).setOnClickListener(v -> {
            dialog.dismiss();
            child.set("设备数据");
            son = "S";
        });
        view.findViewById(R.id.tv_jjsj).setOnClickListener(v -> {
            dialog.dismiss();
            child.set("家具数据");
            son = "J";
        });
        view.findViewById(R.id.tv_rjsj).setOnClickListener(v -> {
            dialog.dismiss();
            child.set("软件数据");
            son = "R";
        });
        view.findViewById(R.id.tv_clsj).setOnClickListener(v -> {
            dialog.dismiss();
            child.set("车辆数据");
            son = "Q";
        });
        view.findViewById(R.id.tv_dzpsj).setOnClickListener(v -> {
            dialog.dismiss();
            child.set("低值品数据");
            son = "D";
        });
        view.findViewById(R.id.tv_all).setOnClickListener(v -> {
            dialog.dismiss();
            child.set("全部");
            son = null;
        });
    }
}
