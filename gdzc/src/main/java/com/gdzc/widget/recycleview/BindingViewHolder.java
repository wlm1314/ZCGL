package com.gdzc.widget.recycleview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 王少岩 on 2016/11/15.
 */

public class BindingViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public BindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setItemClickLister(ItemClickLister itemClickLister) {
        binding.getRoot().setOnClickListener(v -> {
            if (itemClickLister != null)
                itemClickLister.onItemClick(binding.getRoot(), getLayoutPosition());

        });
    }

    public void setItemClickLister(ItemClickLister itemClickLister, int viewId) {
        if (binding.getRoot().findViewById(viewId) != null)
            binding.getRoot().findViewById(viewId).setOnClickListener(v -> {
                if (itemClickLister != null)
                    itemClickLister.onItemClick(binding.getRoot(), getLayoutPosition());
            });
    }

    public interface ItemClickLister {
        void onItemClick(View view, int position);
    }

}
