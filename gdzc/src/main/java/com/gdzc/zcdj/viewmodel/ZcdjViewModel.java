package com.gdzc.zcdj.viewmodel;

import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.utils.NavigateUtils;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjViewModel {
    public final ObservableField<String> flh = new ObservableField<>();
    public final ObservableField<String> dj = new ObservableField<>();
    public ReplyCommand flhCommand = new ReplyCommand(() -> NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), FlhActivity.class, 1000));

    public ReplyCommand zcdjCommand = new ReplyCommand(() -> {
    });
}
