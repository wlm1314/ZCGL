package com.bjprd.zcgl.widget.recycleview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 王少岩 on 2016/11/3.
 */

public class BindingAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {

    protected BindingTool mBindingTool;
    protected List<T> list;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public BindingAdapter(BindingTool bindingTool, List<T> list) {
        this.mBindingTool = bindingTool;
        this.list = list;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mBindingTool.getLayoutId(), parent, false);
        BindingViewHolder bindingViewHolder = new BindingViewHolder(binding);
        setListener(binding, bindingViewHolder, parent);
        return bindingViewHolder;
    }

    private void setListener(ViewDataBinding binding, BindingViewHolder bindingViewHolder, ViewGroup parent) {
        binding.getRoot().setOnClickListener((view) -> {
            if (mOnItemClickListener != null) {
                int position = bindingViewHolder.getAdapterPosition();
                mOnItemClickListener.onItemClick(parent, view, list.get(position), position);
            }
        });

        binding.getRoot().setOnLongClickListener((view) -> {
            if (mOnItemClickListener != null) {
                int position = bindingViewHolder.getAdapterPosition();
                return mOnItemClickListener.onItemLongClick(parent, view, list.get(position), position);
            }
            return false;
        });
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        T t = list.get(position);
        int variableId = mBindingTool.getVariableId();
        holder.getBinding().setVariable(variableId, t);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(T t) {
        int size = list.size();
        list.add(t);
        notifyItemInserted(size);
    }

    public void addAll(List<T> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

}
