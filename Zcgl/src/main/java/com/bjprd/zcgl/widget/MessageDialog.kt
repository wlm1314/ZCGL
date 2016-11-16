package com.bjprd.zcgl.widget

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.TextView

import com.bjprd.zcgl.R


/**
 * Created by 王少岩 on 2016/5/31.
 */
class MessageDialog : Dialog {

    // 变量
    private var mContext: Activity? = null
    private var mListener: OnDialogFinishListener? = null

    private var tvTitle: TextView? = null
    private var tvLeftButton: TextView? = null
    private var tvRightButton: TextView? = null
    private var tvMessage: TextView? = null
    private var line: View? = null

    constructor(context: Activity, msg: String) : super(context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_alert_app)
        mContext = context
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        initControls(msg)
    }

    constructor(context: Activity, msg: String, isShowLeftBtn: Boolean, listener: OnDialogFinishListener) : super(context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_alert_app)
        mContext = context
        mListener = listener
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        initControls(msg)
        if (!isShowLeftBtn) {
            hideLeftBtn()
        }
    }

    private fun initControls(msg: String) {
        tvTitle = findViewById(R.id.tv_dialog_title) as TextView
        tvLeftButton = findViewById(R.id.tv_dialog_left_button) as TextView
        tvRightButton = findViewById(R.id.tv_dialog_right_button) as TextView
        tvMessage = findViewById(R.id.tv_dialog_msg) as TextView
        line = findViewById(R.id.line_h)
        tvTitle!!.text = mContext!!.getString(R.string.app_name)
        tvMessage!!.text = msg
        tvLeftButton!!.setOnClickListener(mOnClickListener)
        tvRightButton!!.setOnClickListener(mOnClickListener)
    }

    fun hideLeftBtn() {
        line!!.visibility = View.GONE
        tvLeftButton!!.visibility = View.GONE
    }

    fun setTitle(title: String) {
        tvTitle!!.visibility = View.VISIBLE
        tvTitle!!.text = title
    }

    fun hideTitle() {
        tvTitle!!.visibility = View.GONE
    }

    fun setMsg(msg: String) {
        tvMessage!!.text = msg
    }

    fun setDialogFinishListener(listener: OnDialogFinishListener) {
        mListener = listener
    }

    private val mOnClickListener = View.OnClickListener { v ->
        val i = v.id
        if (i == R.id.tv_dialog_left_button) {
            if (mListener != null) {
                mListener!!.onCancel()
            }
            this@MessageDialog.dismiss()

        } else if (i == R.id.tv_dialog_right_button) {
            if (mListener != null) {
                mListener!!.onFinish()
            }
            this@MessageDialog.dismiss()

        }
    }

    interface OnDialogFinishListener {
        fun onFinish()
        fun onCancel()
    }
}
