package com.bjprd.zcgl.widget.recycleview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by 王少岩 on 2016/11/15.
 */

public class BindingViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public BindingViewHolder(ViewDataBinding binding) {
        //----〉注意这里是ViewDataBinding
        //因为DataBindingUtil.inflate返回的类型就是ViewDataBinding. ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater, layoutId,parent, attachToParent);
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
