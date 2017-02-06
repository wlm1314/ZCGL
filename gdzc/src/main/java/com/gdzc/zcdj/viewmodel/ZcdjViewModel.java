package com.gdzc.zcdj.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Environment;
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
import com.gdzc.zcdj.model.ZcdjBean;
import com.gdzc.zcdj.view.ZcdjFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private List<ZcdjBean.Zcdj> mList = new ArrayList<>();

    private ZcdjBean.Zcdj mZcdj;
    public String zcImg, fpImg;

    public LydwBean.Lydw lydw;
    public SyfxBean.Syfx syfx;
    public CfdBean.Cfd cfd;
    public RyBean.Ry ry;

    public BindingViewHolder.ItemClickLister mItemClickLister = (view, position) -> {
        mZcdj = mList.get(position);
        switch (mZcdj.columEng) {
            case "lydwh":
                NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
                break;
            case "lyr":
            case "rybh": {
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
            case "cfdmc":
            case "cfdbh": {
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
            case "gzrq":
                showTimePicker("购置日期", mZcdj);
                break;
            default:
                startSyfxActivity(mZcdj.columName, 1002);
                break;
        }
    };

    public BindingViewHolder.TextChangeListener mTextChangeListener = (view, position, s) -> {
        int dj = Integer.valueOf(this.dj.get());
        if (!TextUtils.isEmpty(s)) {
            ZcdjBean.Zcdj zcdj = mList.get(position);
            int num;
            switch (zcdj.columName) {
                case "成批条数":
                    num = Integer.valueOf(s);
                    if (num >= 1) {
                        mList.get(position + 1).editText.set(num * dj + "");
                    }
                    break;
                case "数量":
                    num = Integer.valueOf(s);
                    if (num >= 1) {
                        mList.get(position + 1).editText.set(num * dj + "");
                    }
                    break;
            }
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
            for (ZcdjBean.Zcdj zcdj : mList) {
                if (zcdj.djbt.equals("1") && TextUtils.isEmpty(zcdj.editText.get())) {
                    Utils.showToast(zcdj.columName + "有误");
                    return;
                } else if (!TextUtils.isEmpty(zcdj.editText.get())) {
                    if (zcdj.columName.equals("分类名称"))
                        jsonObj.put("字符字段7", zcdj.editText.get().trim());
                    else
                        jsonObj.put(zcdj.columName, TextUtils.isEmpty(zcdj.code) ? zcdj.editText.get().trim() : zcdj.code);
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
                .subscribe(new ProgressSubscriber<HttpResult<ZcdjBean>>() {
                    @Override
                    public void onNext(HttpResult<ZcdjBean> zcdjBeanHttpResult) {
                        ZcdjBean zcdjBean = zcdjBeanHttpResult.getData();
                        whatsystem = zcdjBeanHttpResult.getWhatsystem();
                        mList.clear();
                        mList.addAll(getZcdjByFlh(mFlh));
                        mList.add(getZcdj("单价", "单价(元)", dj, "1"));
                        if (zcdjBeanHttpResult.containsSQRW()) {
                            mList.add(getZcdj("批量", "成批条数", "1", "0"));
                            mList.add(getZcdj("金额", "金额(元)", dj, "1"));
                            Observable.from(zcdjBean.list)
                                    .filter(bean -> !bean.字段名.equals("出厂号"))
                                    .subscribe(bean -> mList.add(ZcdjBean.Zcdj.castToZcdj(bean)));
                        }
                        if (zcdjBeanHttpResult.containsDJ()) {
                            mList.add(getZcdj("数量", "数量", "1", "0"));
                            mList.add(getZcdj("金额", "金额(元)", dj, "1"));
                            Observable.from(zcdjBean.list)
                                    .subscribe(bean -> mList.add(ZcdjBean.Zcdj.castToZcdj(bean)));
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

    public List<ZcdjBean.Zcdj> getZcdjByFlh(FlhBean.Flh flh) {
        List<ZcdjBean.Zcdj> list = new ArrayList<>();
        list.add(getZcdj("分类号", "分类号", flh.flh, "1"));
        list.add(getZcdj("分类名称", "分类名称", flh.mc, "1"));
        list.add(getZcdj("国标分类号", "国标分类号", flh.czh, "1"));
        list.add(getZcdj("国标分类名", "国标分类名", flh.czm, "1"));
        return list;
    }

    public ZcdjBean.Zcdj getZcdj(String colName, String xsnr, String value, String isQz) {
        ZcdjBean.Zcdj zcdj = new ZcdjBean.Zcdj();
        zcdj.columName = colName;
        zcdj.xsnr = xsnr;
        zcdj.editText.set(value);
        zcdj.isSelected = false;
        zcdj.djbt = "0";
        zcdj.isQz = isQz;
        return zcdj;
    }

    public void showTimePicker(String title, ZcdjBean.Zcdj zcdj) {
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
            zcdj.editText.set(sdf.format(date));
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
                mZcdj.editText.set(lydw.dwName);
                mZcdj.code = lydw.dwId;
                break;
            case 1002:
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                mZcdj.editText.set(syfx.nr.substring(2, syfx.nr.length()));
                mZcdj.code = syfx.nr.substring(0, 1);
                break;
            case 1008:
                ry = (RyBean.Ry) data.getExtras().getSerializable("data");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("rybh"))
                        .subscribe(zcdj -> zcdj.editText.set(ry.人员编号));
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("lyr"))
                        .subscribe(zcdj -> zcdj.editText.set(ry.人员名));
                break;
            case 1009:
                cfd = (CfdBean.Cfd) data.getExtras().getSerializable("data");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("cfdbh"))
                        .subscribe(zcdj -> zcdj.editText.set(cfd.单位编号));
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("cfdmc"))
                        .subscribe(zcdj -> zcdj.editText.set(cfd.单位名称));
                break;
        }
    }

    /**
     * SD卡中自己拍照照片的存储路径
     */
    private static final File SD_CAMERA_DIR = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
    /**
     * SD卡中用于缓存路径
     */
    private static final File CACHE_DIR = App.getAppContext().getCacheDir();
    private static final String PATH_IMAGE_CAMERA = "Camera";

    /**
     * 获取拍摄照片的存储路径
     */
    public File getCameraFile() {
        File filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = SD_CAMERA_DIR;
        } else {
            filePath = new File(CACHE_DIR, PATH_IMAGE_CAMERA);
        }
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String tempFileName = System.currentTimeMillis() + ".jpg";
        return new File(filePath, tempFileName);
    }
}
