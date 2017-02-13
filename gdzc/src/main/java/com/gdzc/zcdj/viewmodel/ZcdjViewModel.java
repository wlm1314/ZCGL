package com.gdzc.zcdj.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;

import com.bigkoo.pickerview.TimePickerView;
import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.cfd.model.CfdBean;
import com.gdzc.cfd.view.CfdActivity;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.lydw.view.LydwActivity;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.net.http.HttpPostParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.ry.model.RyBean;
import com.gdzc.ry.view.RyActivity;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.syfx.view.SyfxActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingViewHolder;
import com.gdzc.zcdj.model.TsxxBean;
import com.gdzc.zcdj.view.ZcdjFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjViewModel {
    private ZcdjFragment mFragment;
    private FlhBean.Flh mFlh;
    private String whatsystem = "";
    private List<TsxxViewModel> mList = new ArrayList<>();

    private TsxxViewModel mTsxxViewModel;
    public String zcImg, fpImg;

    public LydwBean.Lydw lydw;
    public SyfxBean.Syfx syfx;
    public CfdBean.Cfd cfd;
    public RyBean.Ry ry;

    public BindingViewHolder.ItemClickLister mItemClickLister = (view, position) -> {
        mTsxxViewModel = mList.get(position);
        if (mTsxxViewModel.isEditAble.get()) return;
        if (mTsxxViewModel.columType.get().equals("日期型"))
            showTimePicker(mTsxxViewModel.colum.get());
        else if (mTsxxViewModel.isQz.get()) {
            switch (mTsxxViewModel.colum.get()) {
                case "领用单位号":
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
                    break;
                case "领用人":
                case "人员编号": {
                    if (lydw == null) {
                        Utils.showToast("请选择领用单位");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "人员");
                    bundle.putString("dwid", lydw.dwId);
                    bundle.putString("realName", "");
                    bundle.putString("rybh", "");
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), RyActivity.class, 1008, bundle);
                    break;
                }
                case "存放地名称":
                case "存放地编号": {
                    if (lydw == null) {
                        Utils.showToast("请选择领用单位");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "存放地");
                    bundle.putString("dwid", lydw.dwId);
                    bundle.putString("cfdbh", "");
                    bundle.putString("cfdmc", "");
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), CfdActivity.class, 1009, bundle);
                    break;
                }
                default:
                    startSyfxActivity(mTsxxViewModel.colum.get(), 1002);
                    break;
            }
        }
    };

    public BindingViewHolder.TextChangeListener mTextChangeListener = (view, position, s) -> {
        TsxxViewModel temp = mList.get(position);
        if (temp.colum.get().equals("数量") || temp.colum.get().equals("批量")) {
            int dj = Integer.valueOf(this.dj.get());
            if (!TextUtils.isEmpty(s) && Utils.isNumeric(s)) {
                int num = Integer.valueOf(s);
                if (num >= 1) {
                    Observable.from(mList)
                            .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("金额"))
                            .subscribe(tsxxViewModel -> tsxxViewModel.content.set(num * dj + ""));
                }
            } else
                Utils.showToast("请输入数字类型");
        }
    };

    private void startSyfxActivity(String title, int reqCode) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), SyfxActivity.class, reqCode, bundle);
    }

    public ZcdjViewModel(ZcdjFragment fragment) {
        mFragment = fragment;
    }

    public final ObservableField<String> flh = new ObservableField<>();
    public final ObservableField<String> flmc = new ObservableField<>();
    public final ObservableField<String> dj = new ObservableField<>();

    public ReplyCommand flCommand = new ReplyCommand(() -> NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), FlhActivity.class, 1000));
    public ReplyCommand createCommand = new ReplyCommand(() -> getTsxx(flh.get(), dj.get()));

    public ReplyCommand saveCommand = new ReplyCommand(() -> {
        JSONObject jsonObj = new JSONObject();
        try {
            for (TsxxViewModel tsxxViewModel : mList) {
                if (tsxxViewModel.djbt.get().equals("1") && TextUtils.isEmpty(tsxxViewModel.content.get())) {
                    Utils.showToast(tsxxViewModel.colum.get() + "有误");
                    return;
                } else if (!TextUtils.isEmpty(tsxxViewModel.content.get())) {
                    if (tsxxViewModel.colum.get().equals("分类名称"))
                        jsonObj.put("字符字段7", tsxxViewModel.content.get());
                    else
                        jsonObj.put(tsxxViewModel.colum.get(), TextUtils.isEmpty(tsxxViewModel.content.get()) ? tsxxViewModel.id.get() : tsxxViewModel.content.get());
                }
            }
            if (!TextUtils.isEmpty(zcImg))
                jsonObj.put("图片文件", zcImg);
            if (!TextUtils.isEmpty(fpImg))
                jsonObj.put("图片文件1", fpImg);
            createZcdj(whatsystem, jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    });

    //生成表单
    public void getTsxx(String flh, String dj) {
        if (TextUtils.isEmpty(flh)) {
            Utils.showToast("请选择分类号");
            return;
        }
        if (TextUtils.isEmpty(dj)) {
            Utils.showToast("请输入单价");
            return;
        }
        HttpRequest.GetTsxx(HttpPostParams.paramGetTsxx(flh, dj))
                .subscribe(new ProgressSubscriber<HttpResult<TsxxBean>>() {
                    @Override
                    public void onNext(HttpResult<TsxxBean> zcdjBeanHttpResult) {
                        TsxxBean tsxxBean = zcdjBeanHttpResult.getData();
                        whatsystem = zcdjBeanHttpResult.getWhatsystem();
                        mList.addAll(TsxxViewModel.getTsxxViewModelByFlh(mFlh));
                        mList.add(new TsxxViewModel("单价", "单价(元)", "1", "3", dj));
                        if (zcdjBeanHttpResult.containsSQRW()) {
                            mList.add(new TsxxViewModel("批量", "成批条数", "1", "0", "1"));
                            mList.add(new TsxxViewModel("金额", "金额(元)", "1", "3", dj));
                            Observable.from(tsxxBean.list)
                                    .filter(server -> !server.字段名.equals("出厂号") && server.登记否.equals("1"))
                                    .subscribe(server -> {
                                        mList.add(new TsxxViewModel(TsxxBean.Tsxx.castToTsxx(server)));
                                    });
                        }
                        if (zcdjBeanHttpResult.containsDJ()) {
                            mList.add(new TsxxViewModel("数量", "数量", "1", "0", "1"));
                            mList.add(new TsxxViewModel("金额", "金额(元)", "1", "3", dj));
                            Observable.from(tsxxBean.list)
                                    .filter(server -> server.登记否.equals("1"))
                                    .subscribe(server -> {
                                        mList.add(new TsxxViewModel(TsxxBean.Tsxx.castToTsxx(server)));
                                    });
                        }
                        mFragment.setData(mList);
                    }
                });
    }

    //保存表单
    public void createZcdj(String whatsystem, String addnewstr) {
        HttpRequest.AddNew(HttpPostParams.paramAddNew(whatsystem, addnewstr))
                .subscribe(new RetrofitSubscriber<>(baseBean -> {
                    Utils.showToast(baseBean.status.msg);
                    if (baseBean.status.isSuccess()) {
                        mFragment.reset();
                        zcImg = "";
                        fpImg = "";
                    }
                }));
    }

    public void showTimePicker(String title) {
        Date now = new Date();
        TimePickerView mTimePickerView = new TimePickerView(App.getAppContext().getCurrentActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setTitle(title);
        mTimePickerView.setTime(now);
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(date -> {
            if (date.after(new Date())) {
                Utils.showToast("购置日期不能在当前时间之后");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            mTsxxViewModel.content.set(sdf.format(date));
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                mFlh = (FlhBean.Flh) data.getExtras().getSerializable("Flh");
                this.flh.set(mFlh.flh);
                this.flmc.set(mFlh.mc);
                break;
            case 1001:
                lydw = (LydwBean.Lydw) data.getExtras().getSerializable("Lydw");
                mTsxxViewModel.content.set(lydw.dwName);
                mTsxxViewModel.id.set(lydw.dwId);
                break;
            case 1002:
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                mTsxxViewModel.content.set(syfx.nr.substring(2, syfx.nr.length()));
                mTsxxViewModel.id.set(syfx.nr.substring(0, 1));
                break;
            case 1008:
                ry = (RyBean.Ry) data.getExtras().getSerializable("data");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用人"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员名));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("人员编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员编号));
                break;
            case 1009:
                cfd = (CfdBean.Cfd) data.getExtras().getSerializable("data");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地名称"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.单位名称));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.单位编号));
                break;
        }
    }
}
