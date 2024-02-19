package com.android.exercise.domain;

import android.os.Bundle;

/**
 * Created by wangzhen on 2017/2/24.
 */

public class Item extends Generic {
    public String name;
    public Class<?> clazz;
    public Bundle bundle;

    public Item(String name, Class<?> clazz) {
        this(name, clazz, null);
    }

    public Item(String name, Class<?> clazz, Bundle bundle) {
        this.name = name;
        this.clazz = clazz;
        this.bundle = bundle;
    }
}
