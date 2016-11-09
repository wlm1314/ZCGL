package com.binding.bindingadapter.toolbar;

import android.databinding.BindingAdapter;
import android.support.v7.widget.Toolbar;

import com.binding.command.ReplyCommand;


/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @BindingAdapter({"toolbar_navigationIcon"})
    public static void setToolBarNavigation(Toolbar toolbar, int resId) {
        if (resId > 0)
            toolbar.setNavigationIcon(resId);
    }

    @BindingAdapter({"toolbar_logo"})
    public static void setToolBarLogo(Toolbar toolbar, int resId) {
        if (resId > 0)
            toolbar.setLogo(resId);
    }

    @BindingAdapter({"naviCommand"})
    public static void naviCommand(Toolbar toolbar, final ReplyCommand clickCommand) {
        toolbar.setNavigationOnClickListener(v -> {
            if (clickCommand != null) {
                clickCommand.execute();
            }
        });
    }
}

