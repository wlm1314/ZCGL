package com.gdzc.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.gdzc.app.App;
import com.uuzuche.lib_zxing.activity.CaptureActivity;


/**
 * Created by 王少岩 on 2016/10/20.
 */

public class NavigateUtils {

    /**
     * 普通跳转
     *
     * @param activity
     * @param activityClass
     */
    public static void startActivity(Activity activity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 带返回值的跳转
     *
     * @param activity
     * @param activityClass
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, Class<? extends Activity> activityClass, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity activity, Class<? extends Activity> activityClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开拨号页面
     *
     * @param tel
     */
    public static void startPhoneActivity(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getAppContext().getCurrentActivity().startActivity(intent);
    }

    public static void startScanActivity(int req) {
        if (ContextCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(App.getAppContext().getCurrentActivity(), new String[]{Manifest.permission.CAMERA}, req);
        } else
            startActivityForResult(App.getAppContext().getCurrentActivity(), CaptureActivity.class, req);
    }
}
