package com.gdzc.zcdj.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Environment;
import android.text.TextUtils;

import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.model.ZcdjBean;
import com.gdzc.zcdj.view.ZcdjFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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

    public LydwBean.Lydw lydw;
    public SyfxBean.Syfx syfx, jfkm, xz, zcly;

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
        for (ZcdjBean.Zcdj zcdj : mList) {
            if (zcdj.djbt.equals("1") && TextUtils.isEmpty(zcdj.editText.get())) {
                Utils.showToast(zcdj.tsnr);
                return;
            } else if (!TextUtils.isEmpty(zcdj.editText.get())) {
                try {
                    if (zcdj.columEng.equals("lydwh"))
                        jsonObj.put(zcdj.columName, lydw.dwId.trim());
                    else if (zcdj.columEng.equals("syfx"))
                        jsonObj.put(zcdj.columName, syfx.nr.substring(0, 1));
                    else if (zcdj.columEng.equals("jfkem"))
                        jsonObj.put(zcdj.columName, jfkm.nr.substring(0, 1));
                    else if (zcdj.columEng.equals("xz"))
                        jsonObj.put(zcdj.columName, xz.nr.substring(0, 1));
                    else if (zcdj.columEng.equals("zcly"))
                        jsonObj.put(zcdj.columName, zcly.nr.substring(0, 1));
                    else if (zcdj.columName.equals("分类名称"))
                        jsonObj.put("字符字段7", zcdj.editText.get().trim());
                    else
                        jsonObj.put(zcdj.columName, zcdj.editText.get().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        createZcdj(whatsystem, jsonObj.toString());
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
                .subscribe(new RetrofitSubscriber<>(zcdjBean -> {
                    whatsystem = zcdjBean.whatsystem;
                    mList.clear();
                    mList.addAll(getZcdjByFlh(mFlh));
                    mList.add(getZcdj("单价", "单价(元)", dj, "1"));
                    if (zcdjBean.containsSQRW())
                        mList.add(getZcdj("批量", "成批条数", "1", "0"));
                    if (zcdjBean.containsDJ())
                        mList.add(getZcdj("数量", "数量", "1", "0"));
                    mList.add(getZcdj("金额", "金额(元)", dj, "1"));
                    Observable.from(zcdjBean.data.list).subscribe(bean -> mList.add(ZcdjBean.Zcdj.castToZcdj(bean)));
                    mFragment.setData(mList);
                }));
    }

    //保存表单
    public void createZcdj(String whatsystem, String addnewstr) {
        HttpRequest.AddNew(HttpPostParams.paramAddNew(whatsystem, addnewstr))
                .subscribe(new RetrofitSubscriber<>(baseBean -> {
                    Utils.showToast(baseBean.status.msg);
                    if (baseBean.status.isSuccess())
                        mFragment.reset();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case 1000:
                mFlh = (FlhBean.Flh) data.getExtras().getSerializable("Flh");
                this.flh.set(mFlh.flh);
                this.flmc.set(mFlh.mc);
                break;
            case 1001:
                lydw = (LydwBean.Lydw) data.getExtras().getSerializable("Lydw");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("lydwh"))
                        .subscribe(zcdj -> zcdj.editText.set(lydw.dwName));
                break;
            case 1002:
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("syfx"))
                        .subscribe(zcdj -> zcdj.editText.set(syfx.nr.substring(2, syfx.nr.length())));
                break;
            case 1005:
                jfkm = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("jfkem"))
                        .subscribe(zcdj -> zcdj.editText.set(jfkm.nr.substring(2, jfkm.nr.length())));
                break;
            case 1006:
                xz = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("xz"))
                        .subscribe(zcdj -> zcdj.editText.set(xz.nr.substring(2, xz.nr.length())));
                break;
            case 1007:
                zcly = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("zcly"))
                        .subscribe(zcdj -> zcdj.editText.set(zcly.nr.substring(2, zcly.nr.length())));
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
