package com.gdzc.widget.recycleview;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 王少岩 on 2016/11/3.
 */

public class BindingAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {

    private BindingTool mBindingTool;
    private List<T> list;
    private BindingViewHolder.ItemClickLister mItemClickLister;
    private BindingViewHolder.ItemClickLister mItemViewClickLister;
    private int viewId;

    public void setItemClickLister(BindingViewHolder.ItemClickLister itemClickLister) {
        this.mItemClickLister = itemClickLister;
    }

    public void setOnViewClickListener(BindingViewHolder.ItemClickLister itemClickLister, int viewId) {
        this.viewId = viewId;
        this.mItemViewClickLister = itemClickLister;
    }

    public BindingAdapter(BindingTool bindingTool, List<T> list) {
        this.mBindingTool = bindingTool;
        this.list = list;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder bindingViewHolder = new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mBindingTool.getLayoutId(), parent, false));
        bindingViewHolder.setItemClickLister(mItemClickLister);
        bindingViewHolder.setItemClickLister(mItemViewClickLister, viewId);
        return bindingViewHolder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        T t = list.get(position);
        int variableId = mBindingTool.getVariableId();
        holder.getBinding().setVariable(variableId, t);
        convert(holder, t, position);
    }

    public void convert(BindingViewHolder holder, T t, int position){}

    @Override
    public int getItemCount() {
        return list.size();
    }

    public T getItem(int index) {
        return list.get(index);
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

    public void setData(List<T> collection) {
        list = collection;
        notifyItemRangeInserted(0, collection.size());
    }

}
