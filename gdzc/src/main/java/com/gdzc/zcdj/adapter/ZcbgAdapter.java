package com.gdzc.zcdj.adapter;

import android.widget.TextView;

import com.gdzc.R;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.widget.recycleview.BindingViewHolder;
import com.gdzc.zcdj.model.ZcxgBean;

import java.util.List;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcbgAdapter extends BindingAdapter<ZcxgBean.ListBean> {

    public ZcbgAdapter(BindingTool bindingTool, List list) {
        super(bindingTool, list);
    }

    @Override
    public void convert(BindingViewHolder holder, ZcxgBean.ListBean o, int position) {
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_zcmc)).setText(o.资产名称);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_flmc)).setText(o.字符字段7);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_dj)).setText(o.单价);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_sl)).setText(o.数量);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_pl)).setText(o.批量);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_je)).setText(o.金额);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_xh)).setText(o.型号);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_gg)).setText(o.规格);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_cj)).setText(o.厂家);
        ((TextView) holder.getBinding().getRoot().findViewById(R.id.tv_cch)).setText(o.出厂号);
    }
}
