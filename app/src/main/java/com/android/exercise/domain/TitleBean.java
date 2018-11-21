package com.android.exercise.domain;

/**
 * Created by wangzhen on 2017/2/24.
 */

public class TitleBean extends BaseBean {
    private String title;

    public TitleBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
