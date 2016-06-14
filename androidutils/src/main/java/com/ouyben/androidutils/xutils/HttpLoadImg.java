package com.ouyben.androidutils.xutils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * TODO : 图片加载器(封装图片加载,在以后需要的时候可以随时换)
 * Created by owen on 2016-05-14.
 */
public class HttpLoadImg {
    /**
     * TODO: 加载图片,
     *
     * @param activity  传acitivity是为了在 onstop方法的时候停止加载, 在onresume方法中继续加载
     * @param url
     * @param imageView
     */
    public static void loadImgAll(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * TODO: 加载图片,
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImgAll(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

}
