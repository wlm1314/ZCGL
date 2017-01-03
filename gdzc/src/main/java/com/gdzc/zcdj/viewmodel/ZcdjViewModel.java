package com.gdzc.zcdj.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.binding.command.ReplyCommand;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.model.ZcdjBean;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import rx.Observable;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjViewModel {
    public List<ZcdjBean.Zcdj> mZcdjList = new ArrayList<>();
    //登记表单
    public ObservableArrayList<ZcdjBean.Zcdj> items = new ObservableArrayList<>();
    public ItemViewSelector<ZcdjBean.Zcdj> itemView = new BaseItemViewSelector<ZcdjBean.Zcdj>() {
        @Override
        public void select(ItemView itemView, int position, ZcdjBean.Zcdj item) {
            if (TextUtils.isEmpty(item.columName))
                itemView.set(BR.data, R.layout.adapter_zcdj_img_item);
            else
                itemView.set(BR.data, R.layout.adapter_zcdj_item);
        }
    };

    //分类号
    public final ObservableField<FlhBean.Flh> flh = new ObservableField<>();
    //单价
    public final ObservableField<String> dj = new ObservableField<>();
    //分类号跳转
    public ReplyCommand flhCommand = new ReplyCommand(() -> NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), FlhActivity.class, 1000));

    //生成表单
    public ReplyCommand zcdjCommand = new ReplyCommand(() -> {
        if (flh.get() == null) {
            Utils.showToast("请选择分类号");
            return;
        }
        if (TextUtils.isEmpty(dj.get())) {
            Utils.showToast("请输入单价");
            return;
        }
        HttpRequest.GetTsxx(HttpPostParams.paramGetTsxx(flh.get().flh, dj.get()))
                .subscribe(new RetrofitSubscriber<>(
                        zcdjBean -> {
                            mZcdjList.clear();
                            items.clear();
                            items.addAll(getZcdjByFlh(flh.get()));
                            Observable.from(zcdjBean.data.list)
                                    .subscribe(bean -> {
                                        ZcdjBean.Zcdj zcdj = ZcdjBean.Zcdj.castToZcdj(bean);
                                        items.add(zcdj);
                                        mZcdjList.add(zcdj);
                                    });

                            ZcdjBean.Zcdj pl = getZcdj("批量", "", "0");
                            items.add(pl);
                            mZcdjList.add(pl);
                            ZcdjBean.Zcdj sl = getZcdj("数量", "", "0");
                            items.add(sl);
                            mZcdjList.add(sl);
                            ZcdjBean.Zcdj je = getZcdj("金额", "", "0");
                            items.add(je);
                            mZcdjList.add(je);
                            items.add(new ZcdjBean.Zcdj());
                        }
                ));
    });

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
