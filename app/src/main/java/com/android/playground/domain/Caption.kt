package com.android.playground.domain;

/**
 * Created by wangzhen on 2017/2/24.
 */

public class Caption extends Generic {
    private String title;

    public Caption(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
