package com.gdzc.zcdj.viewmodel;

import android.databinding.ObservableField;

import com.gdzc.flh.model.FlhBean;
import com.gdzc.zcdj.model.TsxxBean;

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

    public TsxxBean.Tsxx mTsxx;

    public TsxxViewModel(TsxxBean.Tsxx tsxx) {
        mTsxx = tsxx;
        colum.set(tsxx.columName.trim());
        xsnr.set(tsxx.xsnr.trim());
        columType.set(tsxx.columType);
        djbt.set(tsxx.djbt.trim());
        tsnr.set(tsxx.tsnr.trim());
        isQz.set(tsxx.isQz.equals("1") || tsxx.isQz.equals("2"));
        isEditAble.set((tsxx.isQz.equals("0") || tsxx.isQz.equals("2")) && !tsxx.columType.equals("日期型"));
    }

    public TsxxViewModel(String colum, String xsnr, String djbt, String isQz, String content) {
        this.colum.set(colum);
        this.xsnr.set(xsnr);
        this.djbt.set(djbt);
        this.columType.set("");
        this.isQz.set(isQz.equals("1") || isQz.equals("2"));
        this.content.set(content);
        isEditAble.set(isQz.equals("0") || isQz.equals("2"));
    }

    public static List<TsxxViewModel> getTsxxViewModelByFlh(FlhBean.Flh flh) {
        List<TsxxViewModel> list = new ArrayList<>();
        list.add(new TsxxViewModel("分类号", "分类号", "1", "3", flh.flh));
        list.add(new TsxxViewModel("分类名称", "分类名称", "1", "3", flh.mc));
        list.add(new TsxxViewModel("国标分类号", "国标分类号", "1", "3", flh.czh));
        list.add(new TsxxViewModel("国标分类名", "国标分类名", "1", "3", flh.czm));
        return list;
    }
}
