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
import com.gdzc.databinding.ActivityZcdjEditBinding;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.lydw.view.LydwActivity;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.syfx.view.SyfxActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.model.ZcxgEditBean;

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
    private List<ZcxgEditBean.Zcxg> mList = new ArrayList<>();
    private BindingAdapter<ZcxgEditBean.Zcxg> mAdapter;
    private ZcxgBean.ListBean zcbg;
    private String yqbh = "";

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
        mAdapter = new BindingAdapter<>(new BindingTool(R.layout.adapter_zcxg_edit_item, BR.data), mList);
        mBinding.rvZcbg.setAdapter(mAdapter);
    }

    private void setListener() {
        mAdapter.setOnViewClickListener((view, position) -> {
            switch (mList.get(position).colName) {
                case "领用单位号":
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
                    break;
                case "使用方向":
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), SyfxActivity.class, 1002);
                    break;
                case "购置日期":
                    initTimePicker("购置日期", mList.get(position));
                    break;
            }
        }, R.id.select_layout);

        mBinding.tvCch.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("yqbh", yqbh);
            NavigateUtils.startActivity(this, ZcdjCchEditActivity.class, bundle);
        });
    }

    private void getData() {
        zcbg = (ZcxgBean.ListBean) getIntent().getExtras().getSerializable("zcbg");
        HttpRequest.SearchZJById(HttpPostParams.paramselectZjById(zcbg.id))
                .subscribe(new RetrofitSubscriber<>(zcbgEditBean -> {
                    mList.add(getZcbg("分类号", zcbg.分类号));
                    mList.add(getZcbg("分类名称", zcbg.字符字段7));
                    mList.add(getZcbg("国标分类号", zcbg.国标分类号));
                    mList.add(getZcbg("国标分类名", zcbg.国标分类名));
                    mList.add(getZcbg("单价", zcbg.单价));
                    mList.add(getZcbg("成批条数", zcbg.批量));
                    mList.add(getZcbg("数量", zcbg.数量));
                    mList.add(getZcbg("金额", zcbg.金额));
                    Observable.from(zcbgEditBean.data).subscribe(dataBean -> mList.add(ZcxgEditBean.Zcxg.castToZcxb(dataBean)));
                    Observable.from(zcbgEditBean.data).filter(dataBean -> dataBean.字段名.equals("资产编号")).subscribe(dataBean -> yqbh = dataBean.值);
                    mAdapter.notifyDataSetChanged();
                    if (!zcbg.批量.equals("1")) mBinding.tvCch.setVisibility(View.VISIBLE);
                }));
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
            jsonObj.put("id", zcbg.id);
            for (ZcxgEditBean.Zcxg zcxg : mList) {
                if (zcxg.isNull.equals("1") && TextUtils.isEmpty(zcxg.val.get())) {
                    Utils.showToast(zcxg.xsnr);
                    return true;
                } else if (!TextUtils.isEmpty(zcxg.val.get())) {
                    if (zcxg.colName.equals("领用单位号"))
                        jsonObj.put(zcxg.colName, lydw == null ? zcxg.val.get() : lydw.dwId);
                    else if (zcxg.colName.equals("使用方向"))
                        jsonObj.put(zcxg.colName, syfx == null ? zcxg.val.get() : syfx.校编号);
                    else if (zcxg.colName.equals("分类名称"))
                        jsonObj.put("字符字段7", zcxg.val.get().trim());
                    else if (zcxg.colName.equals("成批条数"))
                        jsonObj.put("批量", zcxg.val.get().trim());
                    else
                        jsonObj.put(zcxg.colName, zcxg.val.get().trim());
                }
            }

            HttpRequest.UpdateZj(HttpPostParams.paramUpdateZj(jsonObj.toString()))
                    .subscribe(new RetrofitSubscriber<>(baseBean -> {
                        Utils.showToast("修改成功");
                        Intent data = new Intent();
                        data.putExtra("update", true);
                        setResult(RESULT_OK, data);
                        finish();
                    }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    private LydwBean.Lydw lydw;
    private SyfxBean.Syfx syfx;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1001:
                lydw = (LydwBean.Lydw) data.getExtras().getSerializable("Lydw");
                Observable.from(mList)
                        .filter(zcbg -> zcbg.colName.equals("领用单位号"))
                        .subscribe(zcbg -> zcbg.val.set(lydw.dwName));
                break;
            case 1002:
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(zcbg -> zcbg.colName.equals("使用方向"))
                        .subscribe(zcbg -> zcbg.val.set(syfx.校名称));
                break;
        }
    }

    public ZcxgEditBean.Zcxg getZcbg(String colName, String value) {
        ZcxgEditBean.Zcxg zcxg = new ZcxgEditBean.Zcxg();
        zcxg.colName = colName;
        zcxg.isNull = "1";
        zcxg.isEdit = "0";
        zcxg.isQz = "0";
        zcxg.val.set(value);
        zcxg.xsnr = colName;
        return zcxg;
    }

    private void initTimePicker(String title, ZcxgEditBean.Zcxg zcxg) {
        TimePickerView mTimePickerView = new TimePickerView(App.getAppContext(), TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setTitle(title);
        mTimePickerView.setTime(new Date());
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(date -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            zcxg.val.set(sdf.format(date));
        });
    }
}
