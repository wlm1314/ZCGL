package com.gdzc.zcdj.view;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.base.BaseBean;
import com.gdzc.databinding.ActivityZcdjBinding;
import com.gdzc.databinding.LayoutPhotoBinding;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.flh.view.FlhActivity;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.lydw.view.LydwActivity;
import com.gdzc.net.HttpPostParams;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.syfx.view.SyfxActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.UploadFile;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.model.ZcdjBean;
import com.gdzc.zcdj.viewmodel.ZcdjViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    private ZcdjViewModel mViewModel;
    private List<ZcdjBean.Zcdj> mList = new ArrayList<>();
    private BindingAdapter mAdapter;
    private FlhBean.Flh mFlh;
    private int dj = 1;
    private String whatsystem = "";
    private MenuItem saveItem;

    /**
     * SD卡中自己拍照照片的存储路径
     */
    public static final File SD_CAMERA_DIR = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
    /**
     * SD卡中用于缓存路径
     */
    public static final File SD_CACHE_DIR = App.getAppContext().getExternalCacheDir();
    public static final File CACHE_DIR = App.getAppContext().getCacheDir();
    public static final String PATH_IMAGE_CAMERA = "Camera";
    private File tempFile;
    private ImageView tempIv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("资产登记", true));
        mViewModel = new ZcdjViewModel();
        mBinding.setViewModel(mViewModel);
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
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_zcdj_item, BR.data), mList);
        mBinding.rvZcdj.setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.tvFlh.setOnClickListener(v -> NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), FlhActivity.class, 1000));
        mBinding.btCreate.setOnClickListener(v -> mViewModel.getTsxx(mBinding.tvFlh.getText().toString(), mBinding.tvDj.getText().toString()));
        mBinding.imgLayout.getRoot().findViewById(R.id.iv_zc).setOnClickListener(v -> initPhotoView((ImageView) v));
        mBinding.imgLayout.getRoot().findViewById(R.id.iv_fp).setOnClickListener(v -> initPhotoView((ImageView) v));
        mAdapter.setOnViewClickListener((view, position) -> {
            ZcdjBean.Zcdj zcdj = mList.get(position);
            switch (zcdj.columEng) {
                case "lydwh":
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
                    break;
                case "syfx":
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), SyfxActivity.class, 1002);
                    break;
                case "gzrq":
                    mViewModel.initTimePicker("购置日期", zcdj);
                    break;
            }
        }, R.id.select_layout);

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
                                mList.get(position + 2).editText.set(num * dj + "");
                            }
                            break;
                        case "数量":
                            num = Integer.valueOf(s);
                            if (num > 1) {
                                mList.get(position + 1).editText.set(num * dj + "");
                            }
                            break;
                    }
                }
            }
        }, R.id.et_text);

    }

    private void initPhotoView(ImageView view) {
        tempIv = view;
        LayoutPhotoBinding choiceBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_photo, null, false);
        Dialog dialog = Utils.showBottomDialog(App.getAppContext().getCurrentActivity(), choiceBinding.getRoot());
        choiceBinding.takePhote.setOnClickListener(v -> {
            tempFile = getCameraFile();
            Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            intentCamera.putExtra("output", Uri.fromFile(tempFile));
            startActivityForResult(intentCamera, 1003);
            dialog.dismiss();
        });
        choiceBinding.selectPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 1004);
            dialog.dismiss();
        });
        choiceBinding.tvCancel.setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * 获取拍摄照片的存储路径
     */
    private File getCameraFile() {
        File filePath = null;
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

    public void setData(ZcdjBean zcdjBean) {
        saveItem.setTitle("保存");
        whatsystem = zcdjBean.whatsystem;
        dj = Integer.valueOf(mBinding.tvDj.getText().toString());
        mList.clear();
        mList.addAll(mViewModel.getZcdjByFlh(mFlh));
        mList.add(mViewModel.getZcdj("单价", mBinding.tvDj.getText().toString(), "1"));
        mList.add(mViewModel.getZcdj("批量", "1", zcdjBean.containsSQR() ? "1" : "0"));
        mList.add(mViewModel.getZcdj("数量", "1", zcdjBean.containsDJ() ? "1" : "0"));
        mList.add(mViewModel.getZcdj("金额", mBinding.tvDj.getText().toString(), "1"));
        Observable.from(zcdjBean.data.list).subscribe(bean -> mList.add(ZcdjBean.Zcdj.castToZcdj(bean)));
        mAdapter.notifyDataSetChanged();
        mBinding.imgLayout.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        saveItem = menu.findItem(R.id.action_right).setTitle("");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        JSONObject jsonObj = new JSONObject();
        for (ZcdjBean.Zcdj zcdj : mList) {
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
        mViewModel.createZcdj(whatsystem, jsonObj.toString());
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
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("lydwh"))
                        .subscribe(zcdj -> zcdj.editText.set(lydw.dwName));
                break;
            case 1002:
                syfx = (SyfxBean.Syfx) data.getExtras().getSerializable("Syfx");
                Observable.from(mList)
                        .filter(zcdj -> zcdj.columEng != null && zcdj.columEng.equals("syfx"))
                        .subscribe(zcdj -> zcdj.editText.set(syfx.校名称));
                break;
            case 1003:
                if (RESULT_OK == resultCode && tempFile != null && tempFile.exists()) {
                    uploadImg();
                }
                break;
            case 1004:
                String filePath = null;
                Uri selectedImage = data.getData();
                if (null != selectedImage) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                        if (filePath == null) {
                            Toast.makeText(this, "不支持网络图片,请从本地选择!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    filePath = data.getAction().replace("file://", "");
                }
                if (null != filePath) {
                    tempFile = new File(filePath);
                    uploadImg();
                }
                break;
        }
    }

    private void uploadImg(){
        final Map<String, File> files = new HashMap<String, File>();
        files.put("uploadfile", tempFile);
        new Thread() {
            @Override
            public void run() {
                try {
                    BaseBean baseBean = UploadFile.post("http://www.bjprd.com.cn:8080/AndroidInterface-0.0.1-SNAPSHOT/zj/imageUpload", HttpPostParams.BaseParams(), files);
                    if (baseBean.status.isSuccess())
                        mHandler.sendEmptyMessage(0);
                    else
                        mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Utils.showToast("上传成");
                    Glide.with(ZcdjActivity.this).load(tempFile).into(tempIv);
                    break;
                case 1:
                    Utils.showToast("上传失败");
                    tempFile = null;
                    Glide.with(ZcdjActivity.this).load(R.mipmap.place_holder).into(tempIv);
                    break;
            }
        }
    };
}
