package com.android.exercise.util;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * 自定义GlideModule，用于生成GlideApp类
 * Created by wangzhen on 2017/6/26.
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(Context context, Registry registry) {
        super.registerComponents(context, registry);
    }
}
