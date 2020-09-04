package com.android.exercise.domain;

import android.os.Bundle;

/**
 * Created by wangzhen on 2017/2/24.
 */

public class ItemBean extends BaseBean {
    public String name;
    public Class<?> clazz;
    public Bundle bundle;

    public ItemBean(String name, Class<?> clazz) {
        this(name, clazz, null);
    }

    public ItemBean(String name, Class<?> clazz, Bundle bundle) {
        this.name = name;
        this.clazz = clazz;
        this.bundle = bundle;
    }
}
