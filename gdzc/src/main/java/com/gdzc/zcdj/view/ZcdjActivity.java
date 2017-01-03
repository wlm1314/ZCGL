package com.gdzc.zcdj.view;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.bigkoo.pickerview.TimePickerView;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjBinding;
import com.gdzc.databinding.LayoutPhotoBinding;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.lydw.view.LydwActivity;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.syfx.view.SyfxActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.widget.recycleview.MultiBindingAdapter;
import com.gdzc.zcdj.model.ZcdjBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    public List<Object> mList = new ArrayList<>();
    private Map<String, BindingTool> mMap = new HashMap<>();
    private MultiBindingAdapter mAdapter;
    private FlhBean.Flh mFlh;
    private TimePickerView mTimePickerView;
    private int dj = 1;
    private String whatsystem = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("资产登记", true));
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mBinding.rvZcdj.setLayoutManager(layoutManager);
        mBinding.rvZcdj.setHasFixedSize(true);
        mBinding.rvZcdj.setNestedScrollingEnabled(false);
        mMap.put(ZcdjBean.Zcdj.class.getSimpleName(), new BindingTool(R.layout.adapter_zcdj_item, BR.data));
        mMap.put(Object.class.getSimpleName(), new BindingTool(R.layout.adapter_zcdj_img_item, BR.data));
        mAdapter = new MultiBindingAdapter(mMap, mList);
        mBinding.rvZcdj.setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.tvFlh.setOnClickListener(v -> NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), FlhActivity.class, 1000));
        mBinding.btCreate.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(mBinding.tvFlh.getText().toString())) {
                        Utils.showToast("请选择分类号");
                        return;
                    }
                    if (TextUtils.isEmpty(mBinding.tvDj.getText().toString())) {
                        Utils.showToast("请输入单价");
                        return;
                    }
                    HttpRequest.GetTsxx(HttpPostParams.paramGetTsxx(mBinding.tvFlh.getText().toString(), mBinding.tvDj.getText().toString()))
                            .subscribe(new RetrofitSubscriber<>(zcdjBean -> {
                                whatsystem = zcdjBean.whatsystem;
                                dj = Integer.valueOf(mBinding.tvDj.getText().toString());
                                mList.clear();
                                mList.addAll(getZcdjByFlh(mFlh));
                                Observable.from(zcdjBean.data.list).subscribe(bean -> mList.add(ZcdjBean.Zcdj.castToZcdj(bean)));
                                mList.add(getZcdj("单价", mBinding.tvDj.getText().toString(), "1"));
                                mList.add(getZcdj("批量", "1", zcdjBean.containsSQR() ? "1" : "0"));
                                mList.add(getZcdj("数量", "1", zcdjBean.containsDJ() ? "1" : "0"));
                                mList.add(getZcdj("金额", mBinding.tvDj.getText().toString(), "1"));
                                mList.add(new Object());
                                mAdapter.notifyDataSetChanged();
                            }));
                }
        );
        mAdapter.setItemViewClickLister((view, position) -> {
            Object o = mList.get(position);
            if (o instanceof ZcdjBean.Zcdj) {
                ZcdjBean.Zcdj zcdj = (ZcdjBean.Zcdj) o;
                switch (zcdj.columEng) {
                    case "lydwh":
                        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
                        break;
                    case "syfx":
                        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), SyfxActivity.class, 1002);
                        break;
                    case "gzrq":
                        initTimePicker("购置日期", zcdj);
                        break;
                }
            } else {
                LayoutPhotoBinding choiceBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_photo, null, false);
                Dialog dialog = Utils.showBottomDialog(App.getAppContext().getCurrentActivity(), choiceBinding.getRoot());
                choiceBinding.tvCancel.setOnClickListener(v -> dialog.dismiss());
            }
        }, R.id.select_layout, R.id.llayout_fp, R.id.llayout_zc);

        mAdapter.setTextChangeListener((view, position, s) -> {
            if (!TextUtils.isEmpty(s)) {
                Object o = mList.get(position);
                if (o instanceof ZcdjBean.Zcdj) {
                    ZcdjBean.Zcdj zcdj = (ZcdjBean.Zcdj) o;
                    int num;
                    switch (zcdj.columName) {
                        case "批量":
                            num = Integer.valueOf(s);
                            if (num > 1) {
                                ((ZcdjBean.Zcdj) mList.get(position + 2)).editText.set(num * dj + "");
                            }
                            break;
                        case "数量":
                            num = Integer.valueOf(s);
                            if (num > 1) {
                                ((ZcdjBean.Zcdj) mList.get(position + 1)).editText.set(num * dj + "");
                            }
                            break;
                    }
                }
            }
        }, R.id.et_text);

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
        for (Object o : mList) {
            if (o instanceof ZcdjBean.Zcdj) {
                ZcdjBean.Zcdj zcdj = (ZcdjBean.Zcdj) o;
                if (zcdj.isNull.equals("1") && TextUtils.isEmpty(zcdj.editText.get())) {
                    Utils.showToast(zcdj.tsnr);
                    return true;
                } else if (!TextUtils.isEmpty(zcdj.editText.get())) {
                    try {
                        if (zcdj.columEng.equals("lydwh"))
                            jsonObj.put(zcdj.columName, lydw.dwId);
                        else if (zcdj.columEng.equals("syfx"))
                            jsonObj.put(zcdj.columName, syfx.校编号);
                        else if (zcdj.columName.equals("分类名称"))
                            jsonObj.put("字符字段7", zcdj.editText.get().trim());
                        else
                            jsonObj.put(zcdj.columName, zcdj.editText.get().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        HttpRequest.AddNew(HttpPostParams.paramAddNew(whatsystem, jsonObj.toString()))
                .subscribe(new RetrofitSubscriber<>(baseBean -> {
                    Utils.showToast(baseBean.status.msg);
                    if (baseBean.status.isSuccess()) finish();
                }));
        return super.onOptionsItemSelected(item);
    }

    private LydwBean.Lydw lydw;
    private SyfxBean.Syfx syfx;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1000:
                FlhBean.Flh flh = (FlhBean.Flh) data.getExtras().getSerializable("Flh");
                mFlh = flh;
                mBinding.tvFlh.setText(mFlh.mc);
                break;
            case 1001:
                lydw = (LydwBean.Lydw) data.getExtras().getSerializable("Lydw");
                Observable.from(mList)
                        .filter(o -> (o instanceof ZcdjBean.Zcdj) && ((ZcdjBean.Zcdj) o).columEng != null && ((ZcdjBean.Zcdj) o).columEng.equals("lydwh"))
                        .map(o -> (ZcdjBean.Zcdj) o)
                        .subscribe(zcdj -> zcdj.editText.set(lydw.dwName));
                break;
            case 1002:
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(o -> (o instanceof ZcdjBean.Zcdj) && ((ZcdjBean.Zcdj) o).columEng != null && ((ZcdjBean.Zcdj) o).columEng.equals("syfx"))
                        .map(o -> (ZcdjBean.Zcdj) o)
                        .subscribe(zcdj -> zcdj.editText.set(syfx.校名称));
                break;
        }
    }

    private void initTimePicker(String title, ZcdjBean.Zcdj zcdj) {
        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setCyclic(false);
        mTimePickerView.setTitle(title);
        mTimePickerView.setTime(new Date());
        mTimePickerView.show();
        mTimePickerView.setOnTimeSelectListener(date -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            zcdj.editText.set(sdf.format(date));
        });
    }

    private List<ZcdjBean.Zcdj> getZcdjByFlh(FlhBean.Flh flh) {
        List<ZcdjBean.Zcdj> list = new ArrayList<>();
        list.add(getZcdj("分类号", flh.flh, "1"));
        list.add(getZcdj("分类名称", flh.mc, "1"));
        list.add(getZcdj("国标分类号", flh.czh, "1"));
        list.add(getZcdj("国标分类名", flh.czm, "1"));
        return list;
    }

    private ZcdjBean.Zcdj getZcdj(String colName, String value, String isQz) {
        ZcdjBean.Zcdj zcdj = new ZcdjBean.Zcdj();
        zcdj.columName = colName;
        zcdj.editText.set(value);
        zcdj.isSelected = false;
        zcdj.isNull = "1";
        zcdj.isQz = isQz;
        return zcdj;
    }
}
