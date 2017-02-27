package com.gdzc.zcdj.zcdj.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.common.recyclerview.InitRecyclerView;
import com.gdzc.databinding.ActivityZcdjEditBinding;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.cch.view.ZcdjCchActivity;
import com.gdzc.zcdj.mk.model.CfdBean;
import com.gdzc.zcdj.mk.model.DwBean;
import com.gdzc.zcdj.mk.model.MKBean;
import com.gdzc.zcdj.mk.model.RyBean;
import com.gdzc.zcdj.mk.view.MKActivity;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.zcdj.model.ZcxgEditBean;
import com.gdzc.zcdj.zcdj.viewmodel.TsxxViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcdjEditActivity extends BaseActivity<ActivityZcdjEditBinding> {
    private List<TsxxViewModel> mList = new ArrayList<>();
    private BindingAdapter<TsxxViewModel> mAdapter;
    private ZcxgBean.Zcxg zcxg;
    private String yqbh = "";
    private TsxxViewModel mTsxxViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj_edit;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("整机", true));
    }

    @Override
    protected void init() {
        initView();
        getData();
        setListener();
    }

    private void initView() {
        InitRecyclerView.initLinearLayoutVERTICAL(this, mBinding.rvZcbg);
        mAdapter = new BindingAdapter<>(new BindingTool(R.layout.adapter_zcdj_item, BR.viewModel), mList);
        mBinding.rvZcbg.setAdapter(mAdapter);
    }

    String dwId = "";

    private void setListener() {
        mAdapter.setItemClickLister((view, position) -> {
            mTsxxViewModel = mList.get(position);
            mTsxxViewModel = mList.get(position);
            if (mTsxxViewModel.columType.get().equals("日期型"))
                showTimePicker(mTsxxViewModel.colum.get());
            else if (mTsxxViewModel.isQz.get()) {
                switch (mTsxxViewModel.colum.get()) {
                    case "领用单位号":
                        startMKActivity("领用单位", 1001);
                        break;
                    case "领用人":
                    case "人员编号": {
                        Observable.from(mList)
                                .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用单位号"))
                                .subscribe(tsxxViewModel -> {
                                    dwId = tsxxViewModel.id.get();
                                    if (TextUtils.isEmpty(dwId))
                                        Utils.showToast("请选择领用单位");
                                });
                        startMKActivity("领用人", 1008);
                        break;
                    }
                    case "存放地名称":
                    case "存放地编号": {
                        Observable.from(mList)
                                .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用单位号"))
                                .subscribe(tsxxViewModel -> {
                                    dwId = tsxxViewModel.id.get();
                                    if (TextUtils.isEmpty(dwId))
                                        Utils.showToast("请选择领用单位");
                                });
                        startMKActivity("存放地", 1009);
                        break;
                    }
                    default:
                        startMKActivity(mTsxxViewModel.colum.get(), 1002);
                        break;
                }
            }
        });

        mBinding.tvCch.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("yqbh", yqbh);
            NavigateUtils.startActivity(this, ZcdjCchActivity.class, bundle);
        });
    }

    private void startMKActivity(String title, int reqCode) {
        Bundle bundle = new Bundle();
        bundle.putString("columName", title);
        bundle.putString("dwId", dwId != null ? dwId : "");
        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), MKActivity.class, reqCode, bundle);
    }

    private void getData() {
        zcxg = (ZcxgBean.Zcxg) getIntent().getExtras().getSerializable("zcxg");
        HttpRequest.SearchZJById(HttpParams.paramselectZjById(zcxg.id))
                .subscribe(new ProgressSubscriber<ArrayList<ZcxgEditBean>>() {
                    @Override
                    public void onNext(ArrayList<ZcxgEditBean> zcxgEditBeen) {
                        mList.addAll(TsxxViewModel.getTsxxViewModelByZcxg(zcxg));
                        Observable.from(zcxgEditBeen)
                                .filter(zcxgEditBean -> zcxgEditBean.修改否.equals("1"))
                                .subscribe(dataBean -> mList.add(new TsxxViewModel(dataBean)));
                        Observable.from(zcxgEditBeen).filter(dataBean -> dataBean.字段名.equals("资产编号")).subscribe(dataBean -> yqbh = dataBean.值);
                        mAdapter.notifyDataSetChanged();
                        if (!zcxg.批量.equals("1")) mBinding.tvCch.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("保存");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", zcxg.id);
            for (TsxxViewModel tsxxViewModel : mList) {
                if (tsxxViewModel.djbt.get().equals("1") && TextUtils.isEmpty(tsxxViewModel.content.get())) {
                    Utils.showToast(tsxxViewModel.xsnr.get() + "有误");
                    return true;
                } else if (!TextUtils.isEmpty(tsxxViewModel.content.get())) {
                    jsonObj.put(tsxxViewModel.colum.get(), TextUtils.isEmpty(tsxxViewModel.id.get().trim()) ? tsxxViewModel.content.get().trim() : tsxxViewModel.id.get().trim());
                }
            }

            HttpRequest.UpdateZj(HttpParams.paramUpdateZj(jsonObj.toString()))
                    .subscribe(new ProgressSubscriber<HttpResult>() {
                        @Override
                        public void onNext(HttpResult httpResult) {
                            Utils.showToast("修改成功");
                            Intent data = new Intent();
                            data.putExtra("update", true);
                            setResult(RESULT_OK, data);
                            finish();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1001:
                DwBean.Dw dw = (DwBean.Dw) data.getExtras().getSerializable("Mk");
                mTsxxViewModel.content.set(dw.dwName);
                mTsxxViewModel.id.set(dw.dwId);
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用人"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(""));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("人员编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(""));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地名称"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(""));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(""));
                break;
            case 1002:
                MKBean.Mk mk = (MKBean.Mk) data.getExtras().getSerializable("Mk");
                mTsxxViewModel.content.set(mk.nr.substring(2, mk.nr.length()));
                mTsxxViewModel.id.set(mk.nr.substring(0, 1));
                break;
            case 1008:
                RyBean.Ry ry = (RyBean.Ry) data.getExtras().getSerializable("Mk");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用人"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员名));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("人员编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员编号));
                break;
            case 1009:
                CfdBean.Cfd cfd = (CfdBean.Cfd) data.getExtras().getSerializable("Mk");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地名称"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.存放地名));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.存放地号));
                break;
        }
    }

    private void showTimePicker(String title) {
        TimePickerView mTimePickerView = new TimePickerView(App.getAppContext().getCurrentActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setTitle(title);
        mTimePickerView.setTime(new Date());
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(date -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            mTsxxViewModel.content.set(sdf.format(date));
        });
    }
}
