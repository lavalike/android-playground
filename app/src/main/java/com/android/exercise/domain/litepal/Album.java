package com.android.exercise.domain.litepal;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * Album
 * Created by wangzhen on 2020/9/15.
 */
public class Album extends LitePalSupport {
    @Column(unique = true, defaultValue = "unknown")
    public String name;
    @Column(index = true)
    public float price;
}