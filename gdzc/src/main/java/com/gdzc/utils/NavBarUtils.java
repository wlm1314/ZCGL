package com.gdzc.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.gdzc.R;
import com.gdzc.base.App;
import com.magicindicator.MagicIndicator;
import com.magicindicator.ViewPagerHelper;
import com.magicindicator.buildins.circlenavigator.ScaleCircleNavigator;
import com.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.List;


/**
 * Created by 王少岩 on 2016/12/6.
 */

public class NavBarUtils {

    //tab导航栏
    public static void setTabs(MagicIndicator indicator, final List<String> tabTitles, final ViewPager viewPager, int resId) {
        CommonNavigator commonNavigator = new CommonNavigator(App.getAppContext());
        commonNavigator.setAdjustMode(true);  // 自适应模式
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return tabTitles == null ? 0 : tabTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(App.getAppContext().getResources().getColor(R.color.text_normal));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, App.getAppContext().getResources().getDimension(resId));
                colorTransitionPagerTitleView.setSelectedColor(App.getAppContext().getResources().getColor(R.color.blue_primary_dark));
                colorTransitionPagerTitleView.setText(tabTitles.get(index));
                colorTransitionPagerTitleView.setOnClickListener(view -> viewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(App.getAppContext().getResources().getColor(R.color.blue_primary_dark));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    public static void setTabs(MagicIndicator indicator, final List<String> tabTitles, final ViewPager viewPager) {
        setTabs(indicator, tabTitles, viewPager, R.dimen.body);
    }

    public static void setCircleIndicator(MagicIndicator indicator, int count, ViewPager viewPager) {
        ScaleCircleNavigator circleNavigator = new ScaleCircleNavigator(App.getAppContext());
        circleNavigator.setCircleCount(count);
        circleNavigator.setNormalCircleColor(App.getAppContext().getResources().getColor(R.color.white));
        circleNavigator.setSelectedCircleColor(App.getAppContext().getResources().getColor(R.color.blue_primary));
        circleNavigator.setMaxRadius(10);
        circleNavigator.setMinRadius(8);
        circleNavigator.setCircleClickListener(index -> viewPager.setCurrentItem(index));
        indicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(indicator, viewPager);

    }
}
