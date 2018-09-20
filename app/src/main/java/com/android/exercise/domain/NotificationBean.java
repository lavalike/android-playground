package com.android.exercise.domain;

import java.io.Serializable;

/**
 * 个推透传消息数据
 * Created by wangzhen on 2018/9/19.
 */
public class NotificationBean implements Serializable {

    /**
     * 区分字段，后期拓展用，为1表示稿件
     */
    private int messageType;
    private int articleId;
    private int doctype;
    private String linkUrl;
    private String title;
    private String content;
    private String summary;
    private int metaDataId;//媒立方稿件ID
    private int pushInfoId;//推送消息ID	用于网脉统计

    NotificationBean(Builder builder) {
        this.messageType = builder.messageType;
        this.articleId = builder.articleId;
        this.doctype = builder.doctype;
        this.linkUrl = builder.linkUrl;
        this.title = builder.title;
        this.content = builder.content;
        this.summary = builder.summary;
        this.metaDataId = builder.metaDataId;
        this.pushInfoId = builder.pushInfoId;
    }

    public int getMetaDataId() {
        return metaDataId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getDoctype() {
        return doctype;
    }

    public void setDoctype(int doctype) {
        this.doctype = doctype;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMetaDataId(int metaDataId) {
        this.metaDataId = metaDataId;
    }

    public int getPushInfoId() {
        return pushInfoId;
    }

    public void setPushInfoId(int pushInfoId) {
        this.pushInfoId = pushInfoId;
    }

    public static class Builder {
        private int messageType;
        private int articleId;
        private int doctype;
        private String linkUrl;
        private String title;
        private String content;
        private String summary;
        private int metaDataId;
        private int pushInfoId;

        public Builder setMessageType(int messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder setArticleId(int articleId) {
            this.articleId = articleId;
            return this;
        }

        public Builder setDoctype(int doctype) {
            this.doctype = doctype;
            return this;
        }

        public Builder setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setMetaDataId(int metaDataId) {
            this.metaDataId = metaDataId;
            return this;
        }

        public Builder setPushInfoId(int pushInfoId) {
            this.pushInfoId = pushInfoId;
            return this;
        }

        public NotificationBean build() {
            return new NotificationBean(this);
        }
    }
}
