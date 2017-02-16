package com.gdzc.zcdj.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.gdzc.flh.model.FlhBean;
import com.gdzc.zcdj.model.TsxxBean;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.model.ZcxgEditBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class TsxxViewModel {
    public final ObservableField<String> id = new ObservableField<>();
    public final ObservableField<String> colum = new ObservableField<>();
    public final ObservableField<String> columType = new ObservableField<>();
    public final ObservableField<String> xsnr = new ObservableField<>();
    public final ObservableField<String> djbt = new ObservableField<>();
    public final ObservableField<String> tsnr = new ObservableField<>();
    public final ObservableField<Boolean> isQz = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<Boolean> isEditAble = new ObservableField<>();
    public final ObservableField<Boolean> isNum = new ObservableField<>();

    public TsxxViewModel(TsxxBean.Tsxx tsxx) {
        colum.set(tsxx.columName.trim());
        xsnr.set(tsxx.xsnr.trim());
        columType.set(tsxx.columType);
        djbt.set(tsxx.djbt.trim());
        tsnr.set(tsxx.tsnr.trim());
        isQz.set(tsxx.isQz.equals("1") || tsxx.isQz.equals("2"));
        isEditAble.set((tsxx.isQz.equals("0") || tsxx.isQz.equals("2")) && !tsxx.columType.equals("日期型"));
        content.set("");
        id.set("");
        isNum.set(false);
    }

    public TsxxViewModel(ZcxgEditBean zcxgBean) {
        colum.set(zcxgBean.字段名.trim());
        xsnr.set(zcxgBean.显示内容.trim());
        columType.set(zcxgBean.字段类型);
        djbt.set(zcxgBean.必填否);
        tsnr.set(zcxgBean.提示内容.trim());
        isQz.set(zcxgBean.代码取值否.equals("1") || zcxgBean.代码取值否.equals("2"));
        isEditAble.set((zcxgBean.代码取值否.equals("0") || zcxgBean.代码取值否.equals("2")) && !zcxgBean.字段类型.equals("日期型"));
        content.set(zcxgBean.get汉字取值());
        id.set(zcxgBean.汉字取值.equals(zcxgBean.值) ?"":zcxgBean.汉字取值.equals("未找到!") ? "" : zcxgBean.get值());
        isNum.set(false);
    }

    public static List<TsxxViewModel> getTsxxViewModelByZcxg(ZcxgBean.Zcxg zcxg) {
        List<TsxxViewModel> list = new ArrayList<>();
        if (!TextUtils.isEmpty(zcxg.分类号))
            list.add(new TsxxViewModel("分类号", "分类号", "1", "3", zcxg.分类号));
        if (!TextUtils.isEmpty(zcxg.字符字段7))
            list.add(new TsxxViewModel("字符字段7", "分类名称", "1", "3", zcxg.字符字段7));
        if (!TextUtils.isEmpty(zcxg.国标分类号))
            list.add(new TsxxViewModel("国标分类号", "国标分类号", "1", "3", zcxg.国标分类号));
        if (!TextUtils.isEmpty(zcxg.国标分类名))
            list.add(new TsxxViewModel("国标分类名", "国标分类名", "1", "3", zcxg.国标分类名));
        if (!TextUtils.isEmpty(zcxg.批量))
            list.add(new TsxxViewModel("批量", "成批条数", "1", "3", zcxg.批量));
        if (!TextUtils.isEmpty(zcxg.数量))
            list.add(new TsxxViewModel("数量", "数量", "1", "3", zcxg.数量));
        if (!TextUtils.isEmpty(zcxg.单价))
            list.add(new TsxxViewModel("单价", "单价(元)", "1", "3", zcxg.单价));
        if (!TextUtils.isEmpty(zcxg.金额))
            list.add(new TsxxViewModel("金额", "金额(元)", "1", "3", zcxg.金额));
        return list;
    }

    public TsxxViewModel(String colum, String xsnr, String djbt, String isQz, String content) {
        this.id.set("");
        this.colum.set(colum);
        this.xsnr.set(xsnr);
        this.djbt.set(djbt);
        this.columType.set("");
        this.isQz.set(isQz.equals("1") || isQz.equals("2"));
        this.content.set(content);
        isEditAble.set(isQz.equals("0") || isQz.equals("2"));
        isNum.set("数量单价金额批量".contains(colum));
    }

    public static List<TsxxViewModel> getTsxxViewModelByFlh(FlhBean.Flh flh) {
        List<TsxxViewModel> list = new ArrayList<>();
        list.add(new TsxxViewModel("分类号", "分类号", "1", "3", flh.flh));
        list.add(new TsxxViewModel("字符字段7", "分类名称", "1", "3", flh.mc));
        list.add(new TsxxViewModel("国标分类号", "国标分类号", "1", "3", flh.czh));
        list.add(new TsxxViewModel("国标分类名", "国标分类名", "1", "3", flh.czm));
        return list;
    }
}
