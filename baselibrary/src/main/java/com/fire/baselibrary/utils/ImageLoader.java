package com.fire.baselibrary.utils;

import android.content.Context;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.fire.baselibrary.R;
import com.fire.baselibrary.base.App;

/**
 *
 * @author fire
 * @date 2017/8/24
 */

public class ImageLoader {
    private static ImageLoader sImageLoader;
    private RequestManager mRequestManager;

    public static ImageLoader getInstance(Context context) {
        if (sImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (sImageLoader == null) {
                    sImageLoader = new ImageLoader(context);
                }
            }
        }
        return sImageLoader;
    }

    private ImageLoader(Context context) {
        mRequestManager = Glide.with(context);
    }

    public static <T> DrawableRequestBuilder loadImage(T model) {
        if (sImageLoader == null) {
            sImageLoader = ImageLoader.getInstance(App.getInstance());
        }
        return sImageLoader.mRequestManager.load(model)
                .placeholder(R.color.bgcolor)
                .error(R.color.bgcolor)
                .centerCrop()
                .dontAnimate();
    }

    public static BitmapTypeRequest loadImage(String model) {
        if (sImageLoader == null) {
            sImageLoader = ImageLoader.getInstance(App.getInstance());
        }
        BitmapTypeRequest tBitmapTypeRequest = sImageLoader.mRequestManager.load(model)
                .asBitmap();
        return tBitmapTypeRequest;
    }

    public static <T> DrawableRequestBuilder loadImage(T model, int resourceId) {
        if (sImageLoader == null) {
            sImageLoader = ImageLoader.getInstance(App.getInstance());
        }
        return sImageLoader.mRequestManager.load(model)
                .placeholder(resourceId)
                .error(resourceId)
                .dontAnimate();
    }


    public static <T> DrawableRequestBuilder loadImagePic(T model) {
        if (sImageLoader == null) {
            sImageLoader = ImageLoader.getInstance(App.getInstance());
        }
        return sImageLoader.mRequestManager.load(model)
                .dontAnimate();
    }



}
