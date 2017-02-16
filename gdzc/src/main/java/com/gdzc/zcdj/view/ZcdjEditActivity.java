package com.gdzc.zcdj.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.cfd.model.CfdBean;
import com.gdzc.cfd.view.CfdActivity;
import com.gdzc.databinding.ActivityZcdjEditBinding;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.lydw.view.LydwActivity;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.net.http.HttpPostParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.ry.model.RyBean;
import com.gdzc.ry.view.RyActivity;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.syfx.view.SyfxActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.model.ZcxgEditBean;
import com.gdzc.zcdj.viewmodel.TsxxViewModel;

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
        mBinding.rvZcbg.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvZcbg.setHasFixedSize(true);
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
                        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
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
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "人员");
                        bundle.putString("dwid", dwId);
                        bundle.putString("realName", "");
                        bundle.putString("rybh", "");
                        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), RyActivity.class, 1008, bundle);
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
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "存放地");
                        bundle.putString("dwid", dwId);
                        bundle.putString("cfdbh", "");
                        bundle.putString("cfdmc", "");
                        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), CfdActivity.class, 1009, bundle);
                        break;
                    }
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putString("title", mTsxxViewModel.colum.get());
                        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), SyfxActivity.class, 1002, bundle);
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

    private void getData() {
        zcxg = (ZcxgBean.Zcxg) getIntent().getExtras().getSerializable("zcxg");
        HttpRequest.SearchZJById(HttpPostParams.paramselectZjById(zcxg.id))
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

            HttpRequest.UpdateZj(HttpPostParams.paramUpdateZj(jsonObj.toString()))
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

    private SyfxBean.Syfx syfx;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1001:
                LydwBean.Lydw lydw = (LydwBean.Lydw) data.getExtras().getSerializable("Lydw");
                mTsxxViewModel.content.set(lydw.dwName);
                mTsxxViewModel.id.set(lydw.dwId);
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
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                mTsxxViewModel.content.set(syfx.nr.substring(2, syfx.nr.length()));
                mTsxxViewModel.id.set(syfx.nr.substring(0, 1));
                break;
            case 1008:
                RyBean.Ry ry = (RyBean.Ry) data.getExtras().getSerializable("data");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用人"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员名));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("人员编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员编号));
                break;
            case 1009:
                CfdBean.Cfd cfd = (CfdBean.Cfd) data.getExtras().getSerializable("data");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地名称"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.单位名称));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.单位编号));
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
