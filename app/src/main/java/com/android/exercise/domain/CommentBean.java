package com.android.exercise.domain;

/**
 * 评论bean
 * Created by wangzhen on 2017/8/10.
 */
public class CommentBean extends Generic {
    private String name;
    private String date;
    private String content;
    private int buildLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBuildLevel() {
        return buildLevel;
    }

    public void setBuildLevel(int buildLevel) {
        this.buildLevel = buildLevel;
    }
}
