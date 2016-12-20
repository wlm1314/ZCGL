package com.gdzc.flh.viewmodel;

import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.utils.Utils;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class FlhViewModel {
    public final ObservableField<String> flh = new ObservableField();
    public ReplyCommand searchCommand = new ReplyCommand(() -> {
        getFlh(flh.get(), 1);
    });

    public void getFlh(String flh, int pageNo) {
        HttpRequest.GetFlh(HttpPostParams.paramGetFlh(flh, "", pageNo + "", "10"))
                .subscribe(new RetrofitSubscriber<>(
                        flhBean -> {
                            ((FlhActivity) App.getAppContext().getCurrentActivity()).setFlhList(flhBean.data.list);
                            ((FlhActivity) App.getAppContext().getCurrentActivity()).setFlh(flh);
                        },
                        throwable -> Utils.showToast(App.getAppContext(), "查询失败")
                ));
    }
}
