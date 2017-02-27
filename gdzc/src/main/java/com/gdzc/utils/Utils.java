package com.gdzc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gdzc.R;
import com.gdzc.app.App;

import java.io.File;


public class Utils {
    private static final String TAG = "Utils";
    private static ProgressDialog mLoadingDialog;

    // 土司
    public static void showToast(String text) {
        Toast.makeText(App.getAppContext(), text, Toast.LENGTH_SHORT).show();
    }

    //显示loading
    public static void showLoading(Activity activity) {
        hideLoading();
        mLoadingDialog = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
        mLoadingDialog.setIndeterminate(true);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setMessage("加载中");
        mLoadingDialog.show();
    }

    //隐藏loading
    public static void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 在屏幕底部显示dialog
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showBottomDialog(Context context, View view) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
        Dialog dialog = new Dialog(context, R.style.DialogStyleBottom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        // 设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = mDisplayMetrics.widthPixels;
        wl.gravity = Gravity.BOTTOM;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);// 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * SD卡中自己拍照照片的存储路径
     */
    private static final File SD_CAMERA_DIR = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
    private static final File CACHE_DIR = App.getAppContext().getCacheDir();
    private static final String PATH_IMAGE_CAMERA = "Camera";

    /**
     * 获取拍摄照片的存储路径
     */
    public static File getCameraFile() {
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
