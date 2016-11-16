package com.bjprd.zcgl.utils

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.graphics.Bitmap
import android.support.design.widget.Snackbar
import android.util.Base64
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.bjprd.zcgl.R
import com.bjprd.zcgl.widget.MessageDialog
import java.io.ByteArrayOutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.*

object Utils {
    private val TAG = "Utils"
    private var progressDialog: ProgressDialog? = null

    // 土司
    fun showToast(context: Context, text: CharSequence) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    //snackbar
    fun showSnackBar(view: View, text: CharSequence) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    //显示loading
    fun showLoading(activity: Activity) {
        progressDialog = ProgressDialog(activity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setMessage("加载中...")
        progressDialog!!.show()
    }

    fun showMsgDialog(activity: Activity, msg: String, listener: MessageDialog.OnDialogFinishListener) {
        val msgDialog = MessageDialog(activity, msg, false, listener)
        msgDialog.show()
    }

    //隐藏loading
    fun hideLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    // UTF8编码
    fun UTF8(str: String?): String {
        var value = "" //默认
        if (null == str) {
            return value
        }
        try {
            value = URLEncoder.encode(str, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
        }

        return value
    }

    /**
     * 获取屏幕宽度

     * @param activity
     * *
     * @return
     */
    fun getWindowWidth(activity: Activity): Int {
        val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        return display.width
    }

    /**
     * 获取屏幕高度

     * @param activity
     * *
     * @return
     */
    fun getWindowHeight(activity: Activity): Int {
        val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        return display.height
    }

    /**
     * 在屏幕中间显示dialog

     * @param context
     * *
     * @param view
     * *
     * @return
     */
    fun showCenterDialog(context: Context, view: View): Dialog {
        val mDisplayMetrics = DisplayMetrics()
        (context.getSystemService(Service.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(mDisplayMetrics)
        val dialog = Dialog(context, R.style.DialogStyleBottom)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val window = dialog.window
        // 设置显示动画
        val wl = window!!.attributes
        wl.width = mDisplayMetrics.widthPixels * 4 / 5
        wl.gravity = Gravity.CENTER

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl)// 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

    /**
     * 在屏幕底部显示dialog

     * @param context
     * *
     * @param view
     * *
     * @return
     */
    fun showBottomDialog(context: Context, view: View): Dialog {
        val mDisplayMetrics = DisplayMetrics()
        (context.getSystemService(Service.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(mDisplayMetrics)
        val dialog = Dialog(context, R.style.DialogStyleBottom)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val window = dialog.window
        // 设置显示动画
        val wl = window!!.attributes
        wl.width = mDisplayMetrics.widthPixels
        wl.gravity = Gravity.BOTTOM

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl)// 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        return dialog
    }

    /**
     * bitmap转String

     * @param bitmap
     * *
     * @return
     */
    fun getImgStr(bitmap: Bitmap): String {
        var imgStr = ""
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val b = stream.toByteArray()
        imgStr = String(Base64.encode(b, Base64.DEFAULT))
        return imgStr
    }

    /**
     * 加密

     * @param val
     * *
     * @return
     */
    fun md5(str: String): String? {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(str.toByteArray())
            val m = md5.digest()//加密
            return _md5(m)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun _md5(b: ByteArray): String {
        val sb = StringBuffer()
        for (i in b.indices) {
            if (Integer.toHexString(0xFF and b[i].toInt()).length == 1)
                sb.append("0").append(Integer.toHexString(0xFF and b[i].toInt()))
            else
                sb.append(Integer.toHexString(0xFF and b[i].toInt()))
        }
        return sb.toString()
    }


    fun waitWhilePopInput(editNum1: EditText?) {
        //等界面绘制完再弹出否则无效果
        val timer = Timer()
        timer.schedule(object : TimerTask() {

            override fun run() {
                if (editNum1 != null) {
                    //设置可获得焦点
                    editNum1.isFocusable = true
                    editNum1.isFocusableInTouchMode = true
                    //请求获得焦点
                    editNum1.requestFocus()
                    //调用系统输入法
                    val inputManager = editNum1
                            .context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.showSoftInput(editNum1, 0)
                }
            }
        }, 200)
    }

}
