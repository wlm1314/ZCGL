package com.gdzc.common.viewmodel;

import android.databinding.ObservableField;

import com.gdzc.common.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class ViewModel<T> {
    public final ObservableField<Boolean> isEmpty = new ObservableField<>();
    public CommonAdapter<ItemViewModel> mAdapter;
    public List<ItemViewModel> mItemViewModels = new ArrayList<>();
    public ItemViewModel<T> mItemViewModel;
}
