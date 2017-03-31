package com.gdzc.zctj.view;

import android.view.View;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZctjBinding;
import com.gdzc.zctj.model.ZctjBean;
import com.gdzc.zctj.viewmodel.ZctjViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/17.
 */

public class ZctjActivity extends BaseActivity<ActivityZctjBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zctj;
    }

    @Override
    protected void initViews() {
        mBinding.setAppbar(new AppBar("资产统计", true));
        mBinding.setViewModel(new ZctjViewModel());
        initBarChart(mBinding.chartBarSl);
        initBarChart(mBinding.chartBarJe);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initBarChart(BarChart barChart) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        barChart.setNoDataText("资产统计");
    }

    int i = 1;
    public void setBarData(ZctjBean zctjBean, String type) {
        i = 0;
        String unit = type.equals("je")?"万元":"台件";
        BarChart barChart = type.equals("je")?mBinding.chartBarJe:mBinding.chartBarSl;
        final List<Data> data = new ArrayList<>();
        Observable.from(zctjBean.data.list).subscribe(zctj -> data.add(new Data(i++, type.equals("je")?zctj.totalMoney:zctj.totalCount, zctj.name)));

        barChart.getXAxis().setValueFormatter((value, axis) -> data.get(Math.min(Math.max((int) value, 0), data.size() - 1)).xAxisValue);
        barChart.getAxisRight().setValueFormatter((value, axis) -> (int) value + unit);
        barChart.getAxisLeft().setValueFormatter((value, axis) -> (int) value + unit);
        setData(barChart, data);
    }

    private void setData(BarChart barChart, List<Data> dataList) {

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

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "");
            set.setColors(colors);
            set.setValueTextColors(colors);

            BarData data = new BarData(set);
            data.setValueTextSize(13f);
            data.setBarWidth(0.8f);

            barChart.setData(data);
            barChart.invalidate();
        }
        barChart.animateX(1400, Easing.EasingOption.EaseInOutQuad);
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

    public void showJeChart() {
        mBinding.chartBarSl.setVisibility(View.GONE);
        mBinding.chartBarJe.setVisibility(View.VISIBLE);
        mBinding.chartBarJe.animateX(1400, Easing.EasingOption.EaseInOutQuad);
    }

    public void showSlChart() {
        mBinding.chartBarSl.setVisibility(View.VISIBLE);
        mBinding.chartBarJe.setVisibility(View.GONE);
        mBinding.chartBarSl.animateX(1400, Easing.EasingOption.EaseInOutQuad);
    }

}
