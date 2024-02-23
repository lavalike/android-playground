package com.android.exercise.domain;

import androidx.core.app.NotificationCompat;

import java.io.Serializable;

/**
 * 个推透传消息数据
 * Created by wangzhen on 2018/9/19.
 */
public class NotificationBean implements Serializable {
    public String linkUrl;
    public String title;
    public String content;
    public String summary;
    /**
     * BigTextStyle样式
     *
     * @see NotificationCompat.BigTextStyle
     */
    public boolean bigTextStyle;
    public boolean fullScreen = false;

    NotificationBean(Builder builder) {
        this.linkUrl = builder.linkUrl;
        this.title = builder.title;
        this.content = builder.content;
        this.summary = builder.summary;
        this.bigTextStyle = builder.bigTextStyle;
        this.fullScreen = builder.fullScreen;
    }

    public static class Builder {
        private String linkUrl;
        private String title;
        private String content;
        private String summary;
        private boolean bigTextStyle = false;
        public boolean fullScreen = false;

        public Builder linkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder bigTextStyle(boolean bigTextStyle) {
            this.bigTextStyle = bigTextStyle;
            return this;
        }

        public Builder fullScreen(boolean fullScreen) {
            this.fullScreen = fullScreen;
            return this;
        }

        public NotificationBean build() {
            return new NotificationBean(this);
        }
    }
}
