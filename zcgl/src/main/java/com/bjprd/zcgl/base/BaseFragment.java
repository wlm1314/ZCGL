package com.bjprd.zcgl.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjprd.zcgl.BR;
import com.bjprd.zcgl.R;


/**
 * Created by 王少岩 on 2016/11/9.
 */

public abstract class BaseFragment extends Fragment {
    protected ViewDataBinding binding;
    private AppBarViewModel mBarViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater, container);
        mBarViewModel = new AppBarViewModel(getActivity());
        return binding.getRoot();
    }

    public abstract void initBinding(LayoutInflater inflater, ViewGroup container);

    public void setTitle(String title){
        mBarViewModel.setTitle(title);
        binding.setVariable(BR.appbar, mBarViewModel);
    }

    public void showLeft(){
        mBarViewModel.setNavigation(R.mipmap.icon_back);
        binding.setVariable(BR.appbar, mBarViewModel);
    }
}
