package com.gdzc.zctj.viewmodel;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;

import com.bigkoo.pickerview.TimePickerView;
import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.databinding.LayoutTypeBinding;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.utils.Utils;
import com.gdzc.zctj.view.ZctjActivity;
import com.kelin.mvvmlight.command.ReplyCommand;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王少岩 on 2017/1/17.
 */

public class ZctjViewModel {
    public ZctjViewModel() {
        category = "6";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        startTime.set("1900-01-01");
        endTime.set(sdf.format(new Date()));
        tjType.set("6大类（含无形资产）");
        chartType.set("数量(台件)");
        createChart();
    }

    private Dialog dialog;
    private String type, category;
    public final ObservableField<String> startTime = new ObservableField<>();
    public final ObservableField<String> endTime = new ObservableField<>();
    public final ObservableField<String> tjType = new ObservableField<>();
    public final ObservableField<String> chartType = new ObservableField<>();
    public final ObservableField<String> type1 = new ObservableField<>();
    public final ObservableField<String> type2 = new ObservableField<>();

    public ReplyCommand startCommand = new ReplyCommand(() -> showTimePicker("开始时间", startTime));
    public ReplyCommand endCommand = new ReplyCommand(() -> showTimePicker("结束时间", endTime));
    public ReplyCommand tjTypeCommand = new ReplyCommand(() -> showTypeView("data"));
    public ReplyCommand chartCommand = new ReplyCommand(() -> showTypeView("chart"));
    public ReplyCommand typeCommand1 = new ReplyCommand(() -> {
        dialog.dismiss();
        switch (type) {
            case "data":
                tjType.set(type1.get());
                category = "6";
                break;
            case "chart":
                chartType.set(type1.get());
                ((ZctjActivity)App.getAppContext().getCurrentActivity()).showSlChart();
                break;
        }
    });
    public ReplyCommand typeCommand2 = new ReplyCommand(() -> {
        dialog.dismiss();
        switch (type) {
            case "data":
                tjType.set(type2.get());
                category = "16";
                break;
            case "chart":
                chartType.set(type2.get());
                ((ZctjActivity)App.getAppContext().getCurrentActivity()).showJeChart();
                break;
        }
    });

    public ReplyCommand createChart = new ReplyCommand(() -> createChart());

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

    public void showTypeView(String type) {
        this.type = type;
        LayoutTypeBinding binding = DataBindingUtil.inflate(App.getAppContext().getCurrentActivity().getLayoutInflater(), R.layout.layout_type, null, false);
        binding.setViewModel(this);
        switch (type) {
            case "data":
                type1.set("6大类（含无形资产）");
                type2.set("16类统计");
                break;
            case "chart":
                type1.set("数量(台件)");
                type2.set("金额(万元)");
                break;
        }
        dialog =  Utils.showBottomDialog(App.getAppContext().getCurrentActivity(), binding.getRoot());
    }

    public void createChart(){
        HttpRequest.SelectMyDataTotalByCategory(HttpParams.paramSelectMyDataTotalByCategory(category, startTime.get(), endTime.get()))
                .subscribe(new RetrofitSubscriber<>(zctjBean -> {
                    ((ZctjActivity) App.getAppContext().getCurrentActivity()).setBarData(zctjBean, "je");
                    ((ZctjActivity) App.getAppContext().getCurrentActivity()).setBarData(zctjBean, "tj");
                }));
    }
}
