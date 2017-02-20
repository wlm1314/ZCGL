package com.gdzc.common.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

/**
 * Created by 王少岩 on 2017/2/7.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    private List<T> mDatas;
    private int layoutId;
    private int brId;
    private ItemClickLister mItemClickLister, mItemViewClickListener;
    private TextChangeListener mTextChangeListener;
    private int[] mViewId, mChangeId;

    public void setItemClickLister(ItemClickLister itemClickLister) {
        mItemClickLister = itemClickLister;
    }

    public void setItemViewClickListener(ItemClickLister itemViewClickListener, int... viewId) {
        mViewId = viewId;
        mItemViewClickListener = itemViewClickListener;
    }

    public void setTextChangeListener(TextChangeListener textChangeListener, int... changeId) {
        mChangeId = changeId;
        mTextChangeListener = textChangeListener;
    }

    public CommonAdapter(List<T> mDatas, int layoutId, int brId) {
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonAdapter.ViewHolder holder, int position) {
        holder.getBinding().setVariable(brId, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface ItemClickLister {
        void onItemClick(int position);
    }

    public interface TextChangeListener {
        void onTextChange(int position, String s);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (mItemClickLister != null)
                    mItemClickLister.onItemClick(getLayoutPosition());
            });
            if (mViewId != null)
                for (int viewId : mViewId) {
                    binding.getRoot().findViewById(viewId).setOnClickListener(v -> {
                        if (mItemViewClickListener != null)
                            mItemViewClickListener.onItemClick(getLayoutPosition());
                    });
                }
            if (mChangeId != null)
                for (int changeId : mChangeId) {
                    ((EditText) binding.getRoot().findViewById(changeId)).addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (mTextChangeListener != null)
                                mTextChangeListener.onTextChange(getLayoutPosition(), s.toString());
                        }
                    });
                }
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
