package com.gdzc.zcdj.view;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZcdjBinding;
import com.gdzc.databinding.LayoutPhotoBinding;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.net.http.HttpPostParams;
import com.gdzc.utils.UploadFile;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.model.UploadImageBean;
import com.gdzc.zcdj.model.ZcdjBean;
import com.gdzc.zcdj.viewmodel.ZcdjViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 王少岩 on 2017/1/12.
 */

public class ZcdjFragment extends BaseFragment<FragmentZcdjBinding> {
    private ZcdjViewModel mViewModel;
    private List<ZcdjBean.Zcdj> mList = new ArrayList<>();
    private File tempFile;
    private ImageView tempIv;
    private BindingAdapter mAdapter;

    private String imgType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zcdj;
    }

    @Override
    protected void setViewModel() {
        mViewModel = new ZcdjViewModel(this);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_zcdj_item, com.gdzc.BR.data), mList);
        mBinding.rvZcdj.setAdapter(mAdapter);
    }

    public void setData(List<ZcdjBean.Zcdj> list) {
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        mBinding.imgLayout.getRoot().setVisibility(View.VISIBLE);
        mBinding.btConfirm.setVisibility(View.VISIBLE);
        mBinding.formLayout.setVisibility(View.GONE);
    }

    private void setListener() {
        mBinding.imgLayout.getRoot().findViewById(R.id.iv_zc).setOnClickListener(v -> initPhotoView((ImageView) v, "zc"));
        mBinding.imgLayout.getRoot().findViewById(R.id.iv_fp).setOnClickListener(v -> initPhotoView((ImageView) v, "fp"));
        mAdapter.setOnViewClickListener(mViewModel.mItemClickLister, R.id.select_layout);
        mAdapter.setTextChangeListener(mViewModel.mTextChangeListener, R.id.et_text);
    }

    private void initPhotoView(ImageView view, String imgType) {
        this.imgType = imgType;
        this.tempIv = view;
        LayoutPhotoBinding choiceBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.layout_photo, null, false);
        Dialog dialog = Utils.showBottomDialog(App.getAppContext().getCurrentActivity(), choiceBinding.getRoot());
        choiceBinding.takePhote.setOnClickListener(v -> {
            tempFile = mViewModel.getCameraFile();
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

    private void uploadImg() {
        Utils.showLoading(getActivity());
        final Map<String, File> files = new HashMap<>();
        files.put("uploadfile", tempFile);
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpResult<UploadImageBean> baseBean = UploadFile.post("http://www.bjprd.com.cn:8080/AndroidInterface-0.0.1-SNAPSHOT/zj/imageUpload", HttpPostParams.BaseParams(), files);
                    if (baseBean.getStatus().isSuccess()) {
                        Message msg = Message.obtain();
                        msg.obj = baseBean.getData();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    } else
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
                    switch (imgType) {
                        case "zc":
                            mViewModel.zcImg = (String) msg.obj;
                            break;
                        case "fp":
                            mViewModel.fpImg = (String) msg.obj;
                            break;
                    }
                    Glide.with(getContext()).load(tempFile).into(tempIv);
                    Utils.showToast("上传成功");
                    Utils.hideLoading();
                    break;
                case 1:
                    tempFile = null;
                    Glide.with(getContext()).load(R.mipmap.place_holder).into(tempIv);
                    Utils.showToast("上传失败");
                    Utils.hideLoading();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                        if (filePath == null) {
                            Toast.makeText(getContext(), "不支持网络图片,请从本地选择!", Toast.LENGTH_SHORT).show();
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

    public void reset() {
        mList.clear();
        mBinding.imgLayout.getRoot().setVisibility(View.GONE);
        mBinding.btConfirm.setVisibility(View.GONE);
        mBinding.formLayout.setVisibility(View.VISIBLE);
        mViewModel.flh.set("");
        mViewModel.flmc.set("");
        mViewModel.dj.set("");
    }
}
