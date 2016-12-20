package com.gdzc.widget.recycleview;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by 王少岩 on 2016/11/3.
 */

public class MultiBindingAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private Map<String, BindingTool> map;
    private List<Object> list;
    private BindingViewHolder.ItemClickLister mItemClickLister;
    private BindingViewHolder.ItemClickLister mItemViewClickLister;
    private int viewId;

    public void setItemClickLister(BindingViewHolder.ItemClickLister itemClickLister) {
        this.mItemClickLister = itemClickLister;
    }

    public void setItemViewClickLister(BindingViewHolder.ItemClickLister itemViewClickLister, int viewId) {
        this.mItemViewClickLister = itemViewClickLister;
        this.viewId = viewId;
    }

    public MultiBindingAdapter(Map<String, BindingTool> map, List<Object> list) {
        this.map = map;
        this.list = list;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder bindingViewHolder = new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
        bindingViewHolder.setItemClickLister(mItemClickLister);
        bindingViewHolder.setItemClickLister(mItemViewClickLister, viewId);
        return bindingViewHolder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        Object t = list.get(position);
        int variableId = map.get(t.getClass().getSimpleName()).getVariableId();
        holder.getBinding().setVariable(variableId, t);
    }

    @Override
    public int getItemViewType(int position) {
        //把它的layoutid直接作为type返回
        return map.get(list.get(position).getClass().getSimpleName()).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Object t) {
        int size = list.size();
        list.add(t);
        notifyItemInserted(size);
    }

    public void addAll(List<Object> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public interface OnViewClickListener {
        void onViewClick(Object t, int position);
    }
}
