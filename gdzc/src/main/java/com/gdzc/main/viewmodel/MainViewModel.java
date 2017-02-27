package com.gdzc.main.viewmodel;

import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.gdzc.app.App;
import com.gdzc.login.view.LoginActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.SPUtils;
import com.gdzc.zccx.view.ZccxActivity;
import com.gdzc.zcdj.zcdj.view.ZcdjActivity;
import com.gdzc.zctj.view.ZctjActivity;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class MainViewModel {
    public final ObservableField<String> username = new ObservableField<>();
    public ReplyCommand zcdjCommon = new ReplyCommand(() -> NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), ZcdjActivity.class));
    public ReplyCommand zccxCommon = new ReplyCommand(() -> NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), ZccxActivity.class));
    public ReplyCommand zcbgCommon = new ReplyCommand(() -> {});
    public ReplyCommand zcqcCommon = new ReplyCommand(() -> {});
    public ReplyCommand zctjCommon = new ReplyCommand(() -> {NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), ZctjActivity.class);});
    public ReplyCommand logoutCommon = new ReplyCommand(() -> {
        SPUtils.onLoginOut();
        NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), LoginActivity.class);
        App.getAppContext().getCurrentActivity().finish();
    });

    public MainViewModel() {
        username.set(SPUtils.getString(SPUtils.kUser_nickname, ""));
    }
}
