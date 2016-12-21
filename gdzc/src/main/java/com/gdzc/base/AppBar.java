package com.gdzc.base;

import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.gdzc.R;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class AppBar {
    public final ObservableField<Integer> navigation = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();

    public ReplyCommand naviCommon = new ReplyCommand(() -> App.getAppContext().getCurrentActivity().finish());

    public AppBar() {
        title.set(null);
        navigation.set(0);
    }

    public AppBar(String title, boolean showLeft) {
        this.title.set(title);
        navigation.set(showLeft ? R.mipmap.icon_back : 0);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void showLeft() {
        navigation.set(R.mipmap.icon_back);
    }
}