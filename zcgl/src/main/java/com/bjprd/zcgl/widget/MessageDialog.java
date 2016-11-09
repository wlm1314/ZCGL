package com.bjprd.zcgl.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bjprd.zcgl.R;


/**
 * Created by 王少岩 on 2016/5/31.
 */
public class MessageDialog extends Dialog {

    // 变量
    private Activity mContext;
    private OnDialogFinishListener mListener;

    private TextView tvTitle;
    private TextView tvLeftButton;
    private TextView tvRightButton;
    private TextView tvMessage;
    private View line;

    public MessageDialog(Activity context, String msg) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert_app);
        mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initControls(msg);
    }

    public MessageDialog(Activity context, String msg, boolean isShowLeftBtn, OnDialogFinishListener listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert_app);
        mContext = context;
        mListener = listener;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initControls(msg);
        if(!isShowLeftBtn){
            hideLeftBtn();
        }
    }

    private void initControls(String msg){
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
        tvLeftButton = (TextView) findViewById(R.id.tv_dialog_left_button);
        tvRightButton = (TextView) findViewById(R.id.tv_dialog_right_button);
        tvMessage = (TextView) findViewById(R.id.tv_dialog_msg);
        line = findViewById(R.id.line_h);
        tvTitle.setText(mContext.getString(R.string.app_name));
        tvMessage.setText(msg);
        tvLeftButton.setOnClickListener(mOnClickListener);
        tvRightButton.setOnClickListener(mOnClickListener);
    }

    public void hideLeftBtn(){
        line.setVisibility(View.GONE);
        tvLeftButton.setVisibility(View.GONE);
    }

    public void setTitle(String title){
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    public void hideTitle(){
        tvTitle.setVisibility(View.GONE);
    }

    public void setMsg(String msg){
        tvMessage.setText(msg);
    }

    public void setDialogFinishListener(OnDialogFinishListener listener){
        mListener = listener;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tv_dialog_left_button) {
                if (mListener != null) {
                    mListener.onCancel();
                }
                MessageDialog.this.dismiss();

            } else if (i == R.id.tv_dialog_right_button) {
                if (mListener != null) {
                    mListener.onFinish();
                }
                MessageDialog.this.dismiss();

            }
        }
    };

    public interface OnDialogFinishListener{
        void onFinish();
        void onCancel();
    }
}
