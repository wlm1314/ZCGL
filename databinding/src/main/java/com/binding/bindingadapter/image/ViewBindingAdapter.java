package com.binding.bindingadapter.image;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @BindingAdapter(value = {"uri", "placeholderImageRes", "errorImageRes", "request_width", "request_height"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 @DrawableRes int placeholderImageRes,
                                 @DrawableRes int errorImageRes,
                                 int width, int height) {
        imageView.setImageResource(placeholderImageRes);
        DrawableTypeRequest<String> dtr = null;
        if (!TextUtils.isEmpty(uri)) dtr = Glide.with(imageView.getContext()).load(uri);
        if (width > 0 && height > 0) dtr.override(width, height);
        if (placeholderImageRes > 0) dtr.placeholder(placeholderImageRes);
        if (errorImageRes > 0) dtr.error(errorImageRes);
        dtr.into(imageView);
    }

}

