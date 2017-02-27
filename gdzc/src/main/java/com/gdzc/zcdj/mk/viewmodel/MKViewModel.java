package com.gdzc.zcdj.mk.viewmodel;

import android.content.Intent;

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
import com.gdzc.utils.SPUtils;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.mk.model.CfdBean;
import com.gdzc.zcdj.mk.model.DwBean;
import com.gdzc.zcdj.mk.model.MKBean;
import com.gdzc.zcdj.mk.model.RyBean;

import java.io.Serializable;

import rx.Observable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class MKViewModel extends ViewModel<Object> {
    private String columName, dwId;

    public MKViewModel(String columName, String dwId) {
        mAdapter = new CommonAdapter<>(mItemViewModels, R.layout.adapter_custom_item, BR.viewModel);
        mAdapter = new CommonAdapter(mItemViewModels, R.layout.adapter_custom_item, BR.viewModel);
        mAdapter.setItemViewClickListener(position -> {
            Observable.from(mItemViewModels).subscribe(flh -> flh.checked.set(false));
            mItemViewModel = mItemViewModels.get(position);
            mItemViewModel.checked.set(true);
        }, R.id.cb);
        isEmpty.set(true);
        this.columName = columName;
        this.dwId = dwId;
        getData();
    }

    public void getData() {
        switch (columName) {
            case "领用单位":
                HttpRequest.GetDwList(HttpParams.paramGetDwList(SPUtils.getString(SPUtils.kUser_dwbh, "00")))
                        .subscribe(new ProgressSubscriber<DwBean>() {
                            @Override
                            public void onNext(DwBean dwBean) {
                                Messenger.getDefault().sendNoMsg("complete");
                                mItemViewModels.clear();
                                Observable.from(dwBean.list)
                                        .subscribe(dw -> {
                                            mItemViewModels.add(new ItemViewModel(dw.dwId.trim(), dw.dwName.trim(), dw, false));
                                        });
                                mAdapter.notifyDataSetChanged();
                                isEmpty.set(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Messenger.getDefault().sendNoMsg("complete");
                            }
                        });
                break;
            case "领用人":
                HttpRequest.GetRyList(HttpParams.paramgetRyk(dwId))
                        .subscribe(new ProgressSubscriber<RyBean>() {
                            @Override
                            public void onNext(RyBean ryBean) {
                                Messenger.getDefault().sendNoMsg("complete");
                                mItemViewModels.clear();
                                Observable.from(ryBean.list)
                                        .subscribe(ry -> {
                                            mItemViewModels.add(new ItemViewModel(ry.人员编号.trim(), ry.人员名.trim(), ry, false));
                                        });
                                mAdapter.notifyDataSetChanged();
                                isEmpty.set(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Messenger.getDefault().sendNoMsg("complete");
                            }
                        });
                break;
            case "存放地":
                HttpRequest.GetCfdList(HttpParams.paramgetCfdd(dwId))
                        .subscribe(new ProgressSubscriber<CfdBean>() {
                            @Override
                            public void onNext(CfdBean cfdBean) {
                                Messenger.getDefault().sendNoMsg("complete");
                                mItemViewModels.clear();
                                Observable.from(cfdBean.list)
                                        .subscribe(cfd -> {
                                            mItemViewModels.add(new ItemViewModel(cfd.存放地号.trim(), cfd.存放地名.trim(), cfd, false));
                                        });
                                mAdapter.notifyDataSetChanged();
                                isEmpty.set(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Messenger.getDefault().sendNoMsg("complete");
                            }
                        });
                break;
            default:
                HttpRequest.GetMkList(HttpParams.paramGetMkList(columName, "1"))
                        .subscribe(new ProgressSubscriber<MKBean>() {
                            @Override
                            public void onNext(MKBean mkBean) {
                                Messenger.getDefault().sendNoMsg("complete");
                                mItemViewModels.clear();
                                Observable.from(mkBean.list)
                                        .subscribe(mk -> {
                                            mItemViewModels.add(new ItemViewModel(mk.getBH(), mk.getMC(), mk, false));
                                        });
                                mAdapter.notifyDataSetChanged();
                                isEmpty.set(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Messenger.getDefault().sendNoMsg("complete");
                            }
                        });
                break;
        }


    }

    public void confirm() {
        if (mItemViewModel == null)
            Utils.showToast("请选择" + columName);
        else {
            Intent data = new Intent();
            Object object = mItemViewModel.data;
            data.putExtra("Mk", (Serializable) object);
            App.getAppContext().getCurrentActivity().setResult(RESULT_OK, data);
            App.getAppContext().getCurrentActivity().finish();
        }
    }
}
