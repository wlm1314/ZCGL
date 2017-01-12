package com.gdzc.zcdj.viewmodel;

import android.text.TextUtils;

import com.bigkoo.pickerview.TimePickerView;
import com.gdzc.base.App;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.model.ZcdjBean;
import com.gdzc.zcdj.view.ZcdjFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjViewModel {
    private ZcdjFragment mFragment;
    public ZcdjViewModel(ZcdjFragment fragment) {
        mFragment = fragment;
    }

    //生成表单
    public void getTsxx(String flh, String dj) {
        if (TextUtils.isEmpty(flh)) {
            Utils.showToast("请选择分类号");
            return;
        }
        if (TextUtils.isEmpty(dj)) {
            Utils.showToast("请输入单价");
            return;
        }
        HttpRequest.GetTsxx(HttpPostParams.paramGetTsxx(flh, dj))
                .subscribe(new RetrofitSubscriber<>(zcdjBean -> mFragment.setData(zcdjBean)));
    }

    //保存表单
    public void createZcdj(String whatsystem, String addnewstr) {
        HttpRequest.AddNew(HttpPostParams.paramAddNew(whatsystem, addnewstr))
                .subscribe(new RetrofitSubscriber<>(baseBean -> {
                    Utils.showToast(baseBean.status.msg);
                    if (baseBean.status.isSuccess())
                        mFragment.reset();
                }));
    }

    public void initTimePicker(String title, ZcdjBean.Zcdj zcdj) {
        TimePickerView mTimePickerView = new TimePickerView(App.getAppContext().getCurrentActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setTitle(title);
        mTimePickerView.setTime(new Date());
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(date -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            zcdj.editText.set(sdf.format(date));
        });
    }

    public List<ZcdjBean.Zcdj> getZcdjByFlh(FlhBean.Flh flh) {
        List<ZcdjBean.Zcdj> list = new ArrayList<>();
        list.add(getZcdj("分类号", flh.flh, "1"));
        list.add(getZcdj("分类名称", flh.mc, "1"));
        list.add(getZcdj("国标分类号", flh.czh, "1"));
        list.add(getZcdj("国标分类名", flh.czm, "1"));
        return list;
    }

    public ZcdjBean.Zcdj getZcdj(String colName, String value, String isQz) {
        ZcdjBean.Zcdj zcdj = new ZcdjBean.Zcdj();
        zcdj.columName = colName;
        zcdj.editText.set(value);
        zcdj.isSelected = false;
        zcdj.isNull = "1";
        zcdj.isQz = isQz;
        return zcdj;
    }
}
