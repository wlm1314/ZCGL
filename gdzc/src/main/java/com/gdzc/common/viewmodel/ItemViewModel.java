package com.gdzc.common.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class ItemViewModel<T> {
    public final ObservableField<String> id = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<Boolean> checked = new ObservableField<>();

    public T data;

    public ItemViewModel(String id, String name, T data, boolean checked) {
        this.id.set(id);
        this.name.set(name);
        this.checked.set(checked);
        this.data = data;
    }
}
