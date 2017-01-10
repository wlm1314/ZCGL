package com.gdzc.widget.recycleview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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

    public void setItemClickLister(ItemClickLister itemClickLister, int... viewId) {
        if (viewId.length > 0)
            for (int id : viewId) {
                View view = binding.getRoot().findViewById(id);
                if (view != null)
                    view.setOnClickListener(v -> {
                        if (itemClickLister != null)
                            itemClickLister.onItemClick(binding.getRoot(), getLayoutPosition());
                    });
            }
    }

    public void addTextChangeListener(TextChangeListener listener, int... viewId) {
        if (listener != null)
            if (viewId.length > 0)
                for (int id : viewId) {
                    EditText et = (EditText) binding.getRoot().findViewById(id);
                    if (et != null)
                        et.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                listener.onTextChange(binding.getRoot(), getLayoutPosition(), s.toString());
                            }
                        });
                }
    }

    public interface ItemClickLister {
        void onItemClick(View view, int position);
    }

    public interface TextChangeListener {
        void onTextChange(View view, int position, String s);
    }

}
