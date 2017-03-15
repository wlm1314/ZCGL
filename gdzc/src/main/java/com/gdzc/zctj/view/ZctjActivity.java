package com.gdzc.zctj.view;

import android.graphics.Color;
import android.view.View;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZctjBinding;
import com.gdzc.zctj.model.ZctjBean;
import com.gdzc.zctj.viewmodel.ZctjViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/17.
 */

public class ZctjActivity extends BaseActivity<ActivityZctjBinding> {
    private PieChart mPieChart;
    private BarChart mBarChart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zctj;
    }

    @Override
    protected void initViews() {
        mBinding.setAppbar(new AppBar("资产统计", true));
        mBinding.setViewModel(new ZctjViewModel());
        initPieChart();
        initBarChart();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initPieChart() {
        mPieChart = mBinding.chartPie;
        //是否使用百分比
        mPieChart.setUsePercentValues(false);
        mPieChart.getDescription().setEnabled(false);

        //圆环距离屏幕上下上下左右的距离
        mPieChart.setExtraOffsets(5, 10, 25, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        //是否显示圆环中间的洞
        mPieChart.setDrawHoleEnabled(true);
        //设置中间洞的颜色
        mPieChart.setHoleColor(Color.WHITE);
        //是否显示洞中间文本
        mPieChart.setDrawCenterText(true);
        //设置环中间的文字
        mPieChart.setCenterText("数量统计（台件）");

        //设置圆环透明度及半径
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setTransparentCircleRadius(61f);

        //设置圆环中间洞的半径
        mPieChart.setHoleRadius(58f);

        //触摸是否可以旋转以及松手后旋转的度数
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);

        mPieChart.setHighlightPerTapEnabled(true);

        //只显示比例
        mPieChart.setDrawEntryLabels(false);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mPieChart.setEntryLabelColor(Color.WHITE);
//        mPieChart.setEntryLabelTypeface(mTfRegular);
        mPieChart.setEntryLabelTextSize(12f);

        mPieChart.setNoDataText("资产统计");
    }

    private void initBarChart() {
        mBarChart = mBinding.chartBar;
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);


        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        mBarChart.setNoDataText("资产统计");
    }

    public void setPieData(ZctjBean zctjBean) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Observable.from(zctjBean.data.list).subscribe(zctj -> entries.add(new PieEntry(zctj.totalCount, zctj.name)));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);

        mPieChart.highlightValues(null);
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mPieChart.invalidate();
    }

    int i = 1;

    public void setBarData(ZctjBean zctjBean) {
        i = 0;
        final List<Data> data = new ArrayList<>();
        Observable.from(zctjBean.data.list).subscribe(zctj -> data.add(new Data(i++, zctj.totalCount, zctj.name)));

        mBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return data.get(Math.min(Math.max((int) value, 0), data.size() - 1)).xAxisValue;
            }
        });
        mBarChart.getAxisRight().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "台件";
            }
        });
        mBarChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "台件";
            }
        });
        setData(data);
    }

    private void setData(List<Data> dataList) {

        ArrayList<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int i = 0; i < dataList.size(); i++) {
            Data d = dataList.get(i);
            BarEntry entry = new BarEntry(d.xValue, d.yValue);
            values.add(entry);
        }

        BarDataSet set;

        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "");
            set.setColors(colors);
            set.setValueTextColors(colors);

            BarData data = new BarData(set);
            data.setValueTextSize(13f);
            data.setBarWidth(0.8f);

            mBarChart.setData(data);
            mBarChart.invalidate();
        }
        mBarChart.animateX(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private class Data {

        public String xAxisValue;
        public float yValue;
        public float xValue;

        public Data(float xValue, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }

    public void showPieChart() {
        mBarChart.setVisibility(View.GONE);
        mPieChart.setVisibility(View.VISIBLE);
        mPieChart.animateX(1400, Easing.EasingOption.EaseInOutQuad);
    }

    public void showBarChart() {
        mBarChart.setVisibility(View.VISIBLE);
        mPieChart.setVisibility(View.GONE);
        mBarChart.animateX(1400, Easing.EasingOption.EaseInOutQuad);
    }

}
