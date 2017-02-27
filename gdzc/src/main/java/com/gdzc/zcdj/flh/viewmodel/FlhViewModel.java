package com.gdzc.zcdj.flh.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.binding.messenger.Messenger;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.common.recyclerview.CommonAdapter;
import com.gdzc.common.viewmodel.ItemViewModel;
import com.gdzc.common.viewmodel.ViewModel;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.flh.model.FlhBean;

import rx.Observable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 王少岩 on 2017/2/7.
 */

public class FlhViewModel extends ViewModel<FlhBean.Flh> {
    public final ObservableField<String> fltext = new ObservableField<>();
    public ReplyCommand searchCommand = new ReplyCommand(() -> refresh());

    private int pageNo = 1;

    public FlhViewModel() {
        mAdapter = new CommonAdapter(mItemViewModels, R.layout.adapter_custom_item, BR.viewModel);
        mAdapter.setItemViewClickListener(position -> {
            Observable.from(mItemViewModels).subscribe(flh -> flh.checked.set(false));
            mItemViewModel = mItemViewModels.get(position);
            mItemViewModel.checked.set(true);
        }, R.id.cb);

        fltext.set("");
        isEmpty.set(true);
        refresh();
    }

    public void confirm() {
        if (mItemViewModel == null)
            Utils.showToast("请选择分类");
        else {
            Intent data = new Intent();
            FlhBean.Flh flh = mItemViewModel.data;
            data.putExtra("Flh", flh);
            App.getAppContext().getCurrentActivity().setResult(RESULT_OK, data);
            App.getAppContext().getCurrentActivity().finish();
        }
    }

    public void refresh() {
        mItemViewModels.clear();
        pageNo = 1;
        getData(pageNo++);
    }

    public void loadMore() {
        getData(pageNo++);
    }

    private void getData(int pageNo) {
        HttpRequest.GetFlh(HttpParams.paramGetFlh(fltext.get(), "", pageNo + ""))
                .subscribe(new ProgressSubscriber<FlhBean>() {
                    @Override
                    public void onNext(FlhBean flhBean) {
                        if (flhBean.isLastPage)
                            Messenger.getDefault().sendNoMsg("nomore");
                        else
                            Messenger.getDefault().sendNoMsg("complete");
                        Observable.from(flhBean.list)
                                .subscribe(flh -> mItemViewModels.add(new ItemViewModel(flh.flh, flh.mc, flh, false)));
                        mAdapter.notifyDataSetChanged();
                        isEmpty.set(mItemViewModels.size() == 0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Messenger.getDefault().sendNoMsg("complete");
                    }
                });
    }
}
