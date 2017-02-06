package com.gdzc.zcdj.view;

import android.text.TextUtils;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjCchEditBinding;
import com.gdzc.net.http.HttpPostParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.model.CchBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王少岩 on 2017/1/13.
 */

public class ZcdjCchEditActivity extends BaseActivity<ActivityZcdjCchEditBinding> {
    private CchBean.Cch cch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj_cch_edit;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBar("出厂号", true));
    }

    @Override
    protected void init() {
        cch = (CchBean.Cch) getIntent().getExtras().getSerializable("Cch");
        mBinding.setData(cch);
        mBinding.btConfirm.setOnClickListener(v -> updateCchById());
    }

    private void updateCchById() {
        if (TextUtils.isEmpty(cch.of_cch.get())) {
            Utils.showToast("请输入出厂号");
            return;
        }
        if (TextUtils.isEmpty(cch.of_lyr.get())) {
            Utils.showToast("请输入领用人");
            return;
        }
        if (TextUtils.isEmpty(cch.of_rybh.get())) {
            Utils.showToast("请输入人员编号");
            return;
        }
        if (TextUtils.isEmpty(cch.of_cfdbh.get())) {
            Utils.showToast("请输入存放地编号");
            return;
        }
        if (TextUtils.isEmpty(cch.of_cfdmc.get())) {
            Utils.showToast("请输入存放地名称");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("id", cch.id);
            json.put("出厂号", cch.of_cch.get());
            json.put("领用人", cch.of_lyr.get());
            json.put("人员编号", cch.of_rybh.get());
            json.put("存放地编号", cch.of_cfdbh.get());
            json.put("存放地名称", cch.of_cfdmc.get());
            HttpRequest.UpdateCchById(HttpPostParams.paramselectUpdateCchById(json.toString()))
                    .subscribe(new RetrofitSubscriber<>(baseBean -> {
                        Utils.showToast("修改成功");
                        setResult(RESULT_OK);
                        finish();
                    }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
