package com.gdzc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.widget.MessageDialog;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    private static final String TAG = "Utils";
    private static ProgressDialog mLoadingDialog;

    // 土司
    public static void showToast(String text) {
        Toast.makeText(App.getAppContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
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

    public static void showMsgDialog(Activity activity, String msg, MessageDialog.OnDialogFinishListener listener) {
        MessageDialog msgDialog = new MessageDialog(activity, msg, true, listener);
        msgDialog.show();
    }

    // UTF8编码
    public static String UTF8(String str) {
        String value = ""; //默认
        if (null == str) {
            return value;
        }
        try {
            value = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        return value;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWindowWidth(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return display.getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getWindowHeight(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return display.getHeight();
    }

    /**
     * 在屏幕中间显示dialog
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showCenterDialog(Context context, View view) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
        Dialog dialog = new Dialog(context, R.style.DialogStyleBottom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        // 设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = mDisplayMetrics.widthPixels * 4 / 5;
        wl.gravity = Gravity.CENTER;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);// 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
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
     * bitmap转String
     *
     * @param bitmap
     * @return
     */
    public static String getImgStr(Bitmap bitmap) {
        String imgStr = "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] b = stream.toByteArray();
        imgStr = new String(Base64.encode(b, Base64.DEFAULT));
        return imgStr;
    }

    /**
     * 加密
     *
     * @param val
     * @return
     */
    public static String md5(String val) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();//加密
            return _md5(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String _md5(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if (Integer.toHexString(0xFF & b[i]).length() == 1)
                sb.append("0").append(Integer.toHexString(0xFF & b[i]));
            else
                sb.append(Integer.toHexString(0xFF & b[i]));
        }
        return sb.toString();
    }


    public static void waitWhilePopInput(final EditText editNum1) {
        //等界面绘制完再弹出否则无效果
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (editNum1 != null) {
                    //设置可获得焦点
                    editNum1.setFocusable(true);
                    editNum1.setFocusableInTouchMode(true);
                    //请求获得焦点
                    editNum1.requestFocus();
                    //调用系统输入法
                    InputMethodManager inputManager = (InputMethodManager) editNum1
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editNum1, 0);
                }
            }
        }, 200);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
