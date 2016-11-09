package com.bjprd.zcgl.main;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.binding.base.ViewModel;
import com.binding.command.ReplyCommand;


/**
 * Created by 王少岩 on 2016/10/19.
 */
public class ItemViewModel implements ViewModel {
    //context
    private Context mContext;
    //field to presenter
    public final ObservableField<String> des = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ReplyCommand itemClickCommand = new ReplyCommand(() -> {
        Toast.makeText(mContext, "aaaaa", Toast.LENGTH_SHORT).show();
    });


    public ItemViewModel(Context context, String des_str, String imageUrl_str) {
        this.mContext = context;
        des.set(des_str);
        imageUrl.set(imageUrl_str);
    }
}
