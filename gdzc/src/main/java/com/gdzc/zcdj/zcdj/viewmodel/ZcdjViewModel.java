package com.gdzc.zcdj.zcdj.viewmodel;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.common.recyclerview.CommonAdapter;
import com.gdzc.databinding.LayoutPhotoBinding;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.SPUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.MessageDialog;
import com.gdzc.zcdj.flh.model.FlhBean;
import com.gdzc.zcdj.flh.view.FlhActivity;
import com.gdzc.zcdj.mk.model.CfdBean;
import com.gdzc.zcdj.mk.model.DwBean;
import com.gdzc.zcdj.mk.model.MKBean;
import com.gdzc.zcdj.mk.model.RyBean;
import com.gdzc.zcdj.mk.view.MKActivity;
import com.gdzc.zcdj.zcdj.model.TsxxBean;
import com.kelin.mvvmlight.command.ReplyCommand;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjViewModel {
    private FlhBean.Flh mFlh;
    public DwBean.Dw mDw;
    private String whatsystem = "";

    public CommonAdapter<TsxxViewModel> mAdapter;
    private List<TsxxViewModel> mList = new ArrayList<>();
    private TsxxViewModel mTsxxViewModel;

    private File tempFile;
    private ImageView tempIv;
    public String zcImg, fpImg, imageType;

    public final ObservableField<String> flh = new ObservableField<>();
    public final ObservableField<String> flmc = new ObservableField<>();
    public final ObservableField<Boolean> isShow = new ObservableField<>();

    public ReplyCommand flCommand = new ReplyCommand(() -> NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), FlhActivity.class, 1000));
    public ReplyCommand createCommand = new ReplyCommand(() -> getTsxx());
    public ReplyCommand saveCommand = new ReplyCommand(() -> save());

    double dj = 1, je = 1;
    int sl = 1, pl = 1;

    public ZcdjViewModel() {
        isShow.set(false);
        mAdapter = new CommonAdapter<>(mList, R.layout.adapter_zcdj_item, BR.viewModel);
        mAdapter.setItemClickLister(position -> {
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
                        if (mDw == null) {
                            Utils.showToast("请选择领用单位");
                            return;
                        }
                        startMKActivity("领用人", 1003);
                        break;
                    }
                    case "存放地名称":
                    case "存放地编号": {
                        if (mDw == null) {
                            Utils.showToast("请选择领用单位");
                            return;
                        }
                        startMKActivity("存放地", 1004);
                        break;
                    }
                    default:
                        startMKActivity(mTsxxViewModel.colum.get(), 1002);
                        break;
                }
            }
        });

        mAdapter.setTextChangeListener((position, s) -> {
            if(s.trim().length()==20){
                Utils.showToast("最多输入20个字");
                return;
            }
            TsxxViewModel temp = mList.get(position);
            if ("TDJ".contains(whatsystem) && "数量单价金额批量".contains(temp.colum.get())) {
                if (!TextUtils.isEmpty(s)) {
                    if (temp.colum.get().equals("数量")) {
                        int num = Integer.valueOf(s);
                        if (pl > 1) {
                            Utils.showToast("数量和批量不能同时大于1");
                            temp.content.set("1");
                            return;
                        }
                        if (num >= 1) {
                            sl = num;
                            Observable.from(mList)
                                    .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("金额"))
                                    .subscribe(tsxxViewModel -> tsxxViewModel.content.set("0"));
                            Observable.from(mList)
                                    .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("单价"))
                                    .subscribe(tsxxViewModel -> tsxxViewModel.content.set("0"));
                        }
                    } else if (temp.colum.get().equals("批量")) {
                        int num = Integer.valueOf(s);
                        if (sl > 1) {
                            Utils.showToast("数量和批量不能同时大于1");
                            temp.content.set("1");
                            return;
                        }
                        if (num >= 1) {
                            Observable.from(mList)
                                    .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("金额"))
                                    .subscribe(tsxxViewModel -> tsxxViewModel.content.set("0"));
                            Observable.from(mList)
                                    .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("单价"))
                                    .subscribe(tsxxViewModel -> tsxxViewModel.content.set("0"));
                            pl = num;
                        }
                    } else if (temp.colum.get().equals("单价")) {
                        if (sl > 1) {
                            temp.content.set(dj + "");
                            return;
                        }
                        if (pl >= 1) {//批量大于1时，可以输入单价，计算金额
                            dj = Double.valueOf(s);
                            je = dj * pl;
                            Observable.from(mList)
                                    .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("金额"))
                                    .subscribe(tsxxViewModel -> tsxxViewModel.content.set(je + ""));
                        }
                    } else if (temp.colum.get().equals("金额")) {
                        if (pl > 1) {
                            temp.content.set(je + "");
                            return;
                        }
                        if (sl >= 1) {//数量大于1时，可以输入金额，计算单价
                            je = Double.valueOf(s);
                            dj = je / sl;
                            NumberFormat nf = NumberFormat.getNumberInstance();
                            nf.setMaximumFractionDigits(2);
                            Observable.from(mList)
                                    .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("单价"))
                                    .subscribe(tsxxViewModel -> tsxxViewModel.content.set(nf.format(dj) + ""));
                        }
                    }
                }
            }
        }, R.id.et);
    }

    private void startMKActivity(String title, int reqCode) {
        Bundle bundle = new Bundle();
        bundle.putString("columName", title);
        bundle.putString("dwId", mDw != null ? mDw.dwId : "");
        NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), MKActivity.class, reqCode, bundle);
    }

    private void save() {
        JSONObject jsonObj = new JSONObject();
        try {
            for (TsxxViewModel tsxxViewModel : mList) {
                if (tsxxViewModel.djbt.get().equals("1") && TextUtils.isEmpty(tsxxViewModel.content.get())) {
                    Utils.showToast(tsxxViewModel.xsnr.get() + "有误");
                    return;
                } else if (!TextUtils.isEmpty(tsxxViewModel.content.get().trim())) {
                    jsonObj.put(tsxxViewModel.colum.get(), TextUtils.isEmpty(tsxxViewModel.id.get().trim()) ? tsxxViewModel.content.get().trim() : tsxxViewModel.id.get().trim());
                }
            }
            if (!TextUtils.isEmpty(zcImg))
                jsonObj.put("图片文件", zcImg);
            if (!TextUtils.isEmpty(fpImg))
                jsonObj.put("图片文件1", fpImg);
            HttpRequest.AddNew(HttpParams.paramAddNew(whatsystem, jsonObj.toString()))
                    .subscribe(new ProgressSubscriber<HttpResult>() {
                        @Override
                        public void onNext(HttpResult httpResult) {
                            if (pl > 1){
                                MessageDialog dialog = new MessageDialog(App.getAppContext().getCurrentActivity(), "可在《修改》中查看或修改《整机》信息并查看或修改《出厂号》");
                                dialog.setTitle("保存成功");
                                dialog.hideLeftBtn();
                                dialog.show();
                            }else
                                Utils.showToast("保存成功");
                            if (httpResult.getStatus().isSuccess()) {
                                reset();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            Utils.showToast("保存失败");
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //页面重置
    private void reset() {
        isShow.set(false);
        mList.clear();
        mAdapter.notifyDataSetChanged();
        zcImg = "";
        fpImg = "";
        mFlh = null;
        flh.set("");
        flmc.set("");
    }

    //生成表单
    public void getTsxx() {
        String dj = "1";
        if (TextUtils.isEmpty(flh.get())) {
            Utils.showToast("请选择分类号");
            return;
        }
        HttpRequest.GetTsxx(HttpParams.paramGetTsxx(flh.get(), dj))
                .subscribe(new ProgressSubscriber<HttpResult<TsxxBean>>() {
                    @Override
                    public void onNext(HttpResult<TsxxBean> zcdjBeanHttpResult) {
                        isShow.set(true);
                        TsxxBean tsxxBean = zcdjBeanHttpResult.getData();
                        whatsystem = zcdjBeanHttpResult.getWhatsystem();
                        mList.clear();
                        mList.addAll(TsxxViewModel.getTsxxViewModelByFlh(mFlh));
                        if (zcdjBeanHttpResult.containsSQRW()) {
                            mList.add(new TsxxViewModel("批量", "成 批  条 数", "1", "0", "1"));
                            mList.add(new TsxxViewModel("单价", "单   价  (元)", "1", "0", dj));
                        }
                        if (zcdjBeanHttpResult.containsTDJ()) {
                            mList.add(new TsxxViewModel("批量", "成 批  条 数", "1", "0", "1"));
                            mList.add(new TsxxViewModel("数量", "数        量", "1", "0", "0"));
                            mList.add(new TsxxViewModel("单价", "单   价  (元)", "1", "0", dj));
                            mList.add(new TsxxViewModel("金额", "金   额  (元)", "1", "0", dj));
                        }
                        Observable.from(tsxxBean.list)
                                .filter(server -> server.登记否.equals("1"))
                                .subscribe(server -> {
                                    mList.add(new TsxxViewModel(TsxxBean.Tsxx.castToTsxx(server)));
                                });
                        Observable.from(mList)
                                .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用人"))
                                .subscribe(tsxxViewModel -> tsxxViewModel.content.set(SPUtils.getString(SPUtils.kUser_nickname, "")));
                        Observable.from(mList)
                                .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("人员编号"))
                                .subscribe(tsxxViewModel -> tsxxViewModel.content.set(SPUtils.getString(SPUtils.kUser_username, "")));
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    //日期控件
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

    public final void imageUploadZc(View view) {
        uploadImage(view, "zc");
    }

    public final void imageUploadFp(View view) {
        uploadImage(view, "fp");
    }

    private void uploadImage(View view, String type) {
        this.tempIv = (ImageView) view;
        this.imageType = type;
        LayoutPhotoBinding choiceBinding = DataBindingUtil.inflate(App.getAppContext().getCurrentActivity().getLayoutInflater(), R.layout.layout_photo, null, false);
        Dialog dialog = Utils.showBottomDialog(App.getAppContext().getCurrentActivity(), choiceBinding.getRoot());
        choiceBinding.takePhote.setOnClickListener(v -> {
            tempFile = Utils.getCameraFile();
            Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            intentCamera.putExtra("output", Uri.fromFile(tempFile));
            App.getAppContext().getCurrentActivity().startActivityForResult(intentCamera, 1005);
            dialog.dismiss();
        });
        choiceBinding.selectPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            App.getAppContext().getCurrentActivity().startActivityForResult(intent, 1006);
            dialog.dismiss();
        });
        choiceBinding.tvCancel.setOnClickListener(v -> dialog.dismiss());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                mFlh = (FlhBean.Flh) data.getExtras().getSerializable("Flh");
                this.flh.set(mFlh.flh);
                this.flmc.set(mFlh.mc);
                break;
            case 1001:
                mDw = (DwBean.Dw) data.getExtras().getSerializable("Mk");
                mTsxxViewModel.content.set(mDw.dwName);
                mTsxxViewModel.id.set(mDw.dwId);
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
            case 1003:
                RyBean.Ry ry = (RyBean.Ry) data.getExtras().getSerializable("Mk");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("领用人"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员名));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("人员编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(ry.人员编号));
                break;
            case 1004:
                CfdBean.Cfd cfd = (CfdBean.Cfd) data.getExtras().getSerializable("Mk");
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地名称"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.存放地名));
                Observable.from(mList)
                        .filter(tsxxViewModel -> tsxxViewModel.colum.get().equals("存放地编号"))
                        .subscribe(tsxxViewModel -> tsxxViewModel.content.set(cfd.存放地号));
                break;
            case 1005:
                Glide.with(App.getAppContext()).load(tempFile).into(tempIv);
                uploadImage();
                break;
            case 1006:
                String filePath = null;
                Uri selectedImage = data.getData();
                if (null != selectedImage) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = App.getAppContext().getCurrentActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                        if (filePath == null) {
                            Utils.showToast("不支持网络图片,请从本地选择!");
                        }
                    }
                } else {
                    filePath = data.getAction().replace("file://", "");
                }
                if (null != filePath) {
                    tempFile = new File(filePath);
                    Glide.with(App.getAppContext()).load(tempFile).into(tempIv);
                    uploadImage();
                }
                break;
        }
    }

    private void uploadImage() {
        HttpRequest.ImageUpload(HttpParams.paramImageUpload(tempFile))
                .subscribe(new ProgressSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        Utils.showToast("上传成功");
                        if (imageType.equals("zc"))
                            zcImg = s;
                        else
                            fpImg = s;
                    }
                });
    }

}
