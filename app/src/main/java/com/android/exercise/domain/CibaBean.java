package com.android.exercise.domain;

/**
 * 词霸每日一句
 * Created by wangzhen on 2017/2/8.
 */

public class CibaBean extends BaseBean {

    /**
     * sid : 2502
     * tts : http://news.iciba.com/admin/tts/2017-02-08-day.mp3
     * content : Do you want to know who you are? Don't ask. Act ! Action will delineate and define you.----Thomas Jefferson
     * note : 你想知道你是什么样的人吗？不要问。行动！行动将会勾画并且定义你。——托马斯·杰斐逊
     * love : 2188
     * translation : 词霸小编：小编曾经一直在迷茫，搞不清自己究竟是怎样的人，也不知想成为什么样的人，更不知能成为什么样的人。然而，时间久了，每一天努力的活着，每一天认真的工作也就渐渐看清了自己以后的模样……
     * picture : http://cdn.iciba.com/news/word/20170208.jpg
     * picture2 : http://cdn.iciba.com/news/word/big_20170208b.jpg
     * caption : 词霸每日一句
     * dateline : 2017-02-08
     * s_pv : 5
     * sp_pv : 0
     * tags : null
     * fenxiang_img : http://cdn.iciba.com/web/news/longweibo/imag/2017-02-08.jpg
     */

    private String sid;
    private String tts;
    private String content;
    private String note;
    private String love;
    private String translation;
    private String picture;
    private String picture2;
    private String caption;
    private String dateline;
    private String s_pv;
    private String sp_pv;
    private Object tags;
    private String fenxiang_img;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getS_pv() {
        return s_pv;
    }

    public void setS_pv(String s_pv) {
        this.s_pv = s_pv;
    }

    public String getSp_pv() {
        return sp_pv;
    }

    public void setSp_pv(String sp_pv) {
        this.sp_pv = sp_pv;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public String getFenxiang_img() {
        return fenxiang_img;
    }

    public void setFenxiang_img(String fenxiang_img) {
        this.fenxiang_img = fenxiang_img;
    }
}
