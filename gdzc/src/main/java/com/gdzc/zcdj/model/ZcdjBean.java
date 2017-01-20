package com.gdzc.zcdj.model;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.bigkoo.pickerview.TimePickerView;
import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.base.BaseBean;
import com.gdzc.lydw.view.LydwActivity;
import com.gdzc.syfx.view.SyfxActivity;
import com.gdzc.utils.NavigateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public List<ServerBean> list;
    }

    public static class ServerBean {
        public String id;
        public String 序号;
        public String 字段名;
        public String 英文字段;
        public String 修改否;
        public String 必填否;
        public String 提示内容;
        public String 显示内容;
        public String 模块显示;
        public String 登记否;
        public String 代码取值否;
        public String 默认值;
        public String 字段类型;
        public String 字段长度;
        public String 查询显示;
        public String 登记必填;
        public String 卡片显示;
    }

    public static class Zcdj {
        public String id;
        public String num;
        public String columName;
        public String columEng="";
        public String isEdit;
        public String isNull;
        public String tsnr;
        public String xsnr;
        public String mkxs;
        public String isDj;
        public String isQz;
        public String defValue;
        public String columType;
        public String columLen;
        public String cxxs;
        public String djbt;
        public String kpxs;

        public String getIsQz() {
            if (columEng.equals("gzrq"))
                isQz = "1";
            return isQz;
        }

        public static boolean contains(String str){
            return "批量数量".contains(str);
        }

        public boolean isSelected = true;

        public ObservableField<String> editText = new ObservableField<>();

        public static Zcdj castToZcdj(ServerBean bean) {
            Zcdj zcdj = new Zcdj();
            zcdj.id = bean.id;
            zcdj.num = bean.序号;
            zcdj.columName = bean.字段名;
            zcdj.columEng = bean.英文字段;
            zcdj.isEdit = bean.修改否;
            zcdj.isNull = bean.必填否;
            zcdj.tsnr = bean.提示内容;
            zcdj.xsnr = bean.显示内容;
            zcdj.mkxs = bean.模块显示;
            zcdj.isDj = bean.登记否;
            zcdj.isQz = bean.代码取值否;
            zcdj.defValue = bean.默认值;
            zcdj.columType = bean.字段类型;
            zcdj.columLen = bean.字段长度;
            zcdj.cxxs = bean.查询显示;
            zcdj.djbt = bean.登记必填;
            zcdj.kpxs = bean.卡片显示;
            return zcdj;
        }

        public ReplyCommand clickCommand = new ReplyCommand<Zcdj>(zcdj -> {
            switch (zcdj.columEng) {
                case "lydwh":
                    NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), LydwActivity.class, 1001);
                    break;
                case "syfx":
                    startSyfxActivity("使用方向", 1002);
                    break;
                case "jfkem":
                    startSyfxActivity("经费科目", 1005);
                    break;
                case "xz":
                    startSyfxActivity("现状", 1006);
                    break;
                case "zcly":
                    startSyfxActivity("资产来源", 1007);
                    break;
                case "gzrq":
                    showTimePicker("购置日期", zcdj);
                    break;
            }
        });

        private void startSyfxActivity(String title, int reqCode) {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), SyfxActivity.class, reqCode, bundle);
        }

        public void showTimePicker(String title, ZcdjBean.Zcdj zcdj) {
            TimePickerView mTimePickerView = new TimePickerView(App.getAppContext().getCurrentActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
            mTimePickerView.setCyclic(false);
            mTimePickerView.setTitle(title);
            mTimePickerView.setTime(new Date());
            mTimePickerView.show();
            mTimePickerView.setOnTimeSelectListener(date -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                zcdj.editText.set(sdf.format(date));
            });
        }
    }
}
